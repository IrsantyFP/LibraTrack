package ifp.mobile.projek;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements BookAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private BooksDAO booksDAO;
    private BooksDatabase db;
    private BookAdapter adapter;
    private List<Books> dataset = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = v.findViewById(R.id.rvBuku);
        // Menambahkan listener untuk menangani klik
        adapter = new BookAdapter(getContext(), dataset, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Setup database
        this.db = Room.databaseBuilder(getContext(), BooksDatabase.class, "books_db")
                .fallbackToDestructiveMigration()
                .build();
        this.booksDAO = this.db.booksDao();

        loadData();

        return v;
    }

    private void loadData() {
        Thread t = new Thread(() -> {
            booksDAO.deleteAllBooks(); // Hapus data lama

            // Tambahkan data baru
            Books books1 = new Books();
            books1.title = "Pulang";
            books1.author = "Tere Liye";
            books1.image = R.drawable.cover1; // Ganti dengan ID drawable yang sesuai

            Books books2 = new Books();
            books2.title = "Laskar Pelangi";
            books2.author = "Andrea Hirata";
            books2.image = R.drawable.cover2; // Ganti dengan ID drawable yang sesuai

            Books books3 = new Books();
            books3.title = "Perahu Kertas";
            books3.author = "Dee Lestari";
            books3.image = R.drawable.cover3; // Ganti dengan ID drawable yang sesuai

            Books books4 = new Books();
            books4.title = "Garis Waktu";
            books4.author = "Fiersa Besari";
            books4.image = R.drawable.cover4; // Ganti dengan ID drawable yang sesuai

            booksDAO.insertAll(books1, books2, books3, books4);

            // Ambil data dari database
            List<Books> books = booksDAO.getAllBooks();
            dataset.clear();
            dataset.addAll(books);

            getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
        });
        t.start();
    }

    @Override
    public void onItemClick(int position) {
        Books selectedBook = dataset.get(position);

        // Navigasi ke BookDetailActivity dengan data buku
        Intent intent = new Intent(getActivity(), DetailBuku.class);
        intent.putExtra("book_title", selectedBook.title);
        intent.putExtra("book_author", selectedBook.author);
        intent.putExtra("book_image", selectedBook.image); // Passing image drawable ID
        startActivity(intent);

    }
}