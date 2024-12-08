package ifp.mobile.projek;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

public class DetailBuku extends AppCompatActivity {

    private TextView bookTitle, bookAuthor;
    private ImageView bookImage;
    private BookmarkDatabase db;
    private BookmarkDao bukuDao;
    private Handler handler;
    private List<ListBuku> dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_buku);

        Button likeButton = findViewById(R.id.likeButton);
        Button borrowButton = findViewById(R.id.borrowButton);

        bookTitle = findViewById(R.id.tvJudul);
        bookAuthor = findViewById(R.id.tvPenulis);
        TextView tvTahun = findViewById(R.id.tvTahun);
        bookImage = findViewById(R.id.bookCover);


        String title = getIntent().getStringExtra("book_title");
        String author = getIntent().getStringExtra("book_author");
        int imageResId = getIntent().getIntExtra("book_image", 0); // Gambar akan dikirimkan sebagai resource ID

        if (title != null && author != null && imageResId != 0) {
            bookTitle.setText(title);
            bookAuthor.setText(author);
            bookImage.setImageResource(imageResId);
        }

        this.db = BookmarkDatabase.getInstance(getApplicationContext());
        this.bukuDao = this.db.bukuDao();
        // Listener untuk likeButton
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(() -> {
                    String judul = bookTitle.getText().toString();
                    String penulis = bookAuthor.getText().toString();
                    String tahun = tvTahun.getText().toString();

                    ListBuku buku = new ListBuku();
                    buku.id = (int) (Math.random() * 1000000);
                    buku.judul = judul;
                    buku.penulis = penulis;
                    buku.tahun = tahun;
                    buku.cover = imageResId;

                    bukuDao.insertAll(buku);

                    runOnUiThread(() -> {
                        Toast.makeText(DetailBuku.this, "Buku berhasil disimpan!", Toast.LENGTH_SHORT).show();
                    });
                    finish();

                });
                t.start();
            }
        });


        // Listener untuk borrowButton
        borrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat AlertDialog konfirmasi Yes/No
                new AlertDialog.Builder(DetailBuku.this)
                        .setTitle("Confirmation")
                        .setMessage("Apakah anda yakin ingin meminjam buku ini?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Aksi ketika tombol "Yes" ditekan
                                dialog.dismiss();
                                LacakDatabase db = LacakDatabase.getInstance(getApplicationContext());
                                LacakDao lacakDao = db.lacakDao(); // Ambil instance DAO

                                // Membuat objek ListBukuPinjam dengan data yang didapat dari intent
                                ListBukuPinjam bukuPinjam = new ListBukuPinjam();
                                bukuPinjam.judul = bookTitle.getText().toString();
                                bukuPinjam.imageResource = imageResId;
                                bukuPinjam.isSelesai = false;
                                new Thread(() -> {
                                    lacakDao.insertAll(bukuPinjam);  // Menyisipkan buku baru ke database
                                    runOnUiThread(() -> {
                                        // Menampilkan toast ketika "Yes" ditekan
                                        Toast.makeText(DetailBuku.this, "Buku berhasil ditambahkan!!", Toast.LENGTH_SHORT).show();
                                    });
                                    finish();
                                }).start();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Menutup dialog ketika "No" ditekan
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert) // Optional: Menambahkan icon alert
                        .show(); // Menampilkan AlertDialog
            }
        });
    }
}
