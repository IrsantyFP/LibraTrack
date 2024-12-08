package ifp.mobile.projek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    private LacakDao lacakDao;
    private LacakDatabase db;

    public interface OnItemClickListener {
        void onReturnClick(ListBukuPinjam buku);
    }

    private final Context ctx;
    private final List<?> data;
    private final boolean isCompletedList;
    private OnItemClickListener listener;

    public TrackAdapter(Context ctx, List<?> data, boolean isCompletedList) {
        this.ctx = ctx;
        this.data = data;
        this.isCompletedList = isCompletedList;
        db = LacakDatabase.getInstance(ctx);
        lacakDao = db.lacakDao();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class TrackViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul;
        Button dueDateButton, statusButton;
        ImageView bookImage;

        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tvJudul);
            dueDateButton = itemView.findViewById(R.id.btTenggat);
            statusButton = itemView.findViewById(R.id.btStatus);
            bookImage = itemView.findViewById(R.id.book_image);
        }
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = isCompletedList ? R.layout.rowview_track_selesai : R.layout.rowview_track;
        View view = LayoutInflater.from(ctx).inflate(layoutId, parent, false);
        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        if (isCompletedList) {
            ListBukuPinjam buku = (ListBukuPinjam) data.get(position);
            holder.tvJudul.setText(buku.judul);
            holder.statusButton.setText("Sudah dikembalikan");
            holder.statusButton.setEnabled(false);
            holder.bookImage.setImageResource(buku.imageResource);
        } else {
            // Jika daftar berlangsung
            ListBukuPinjam buku = (ListBukuPinjam) data.get(position);
            holder.tvJudul.setText(buku.judul);
            holder.statusButton.setText("Sudah selesai");
            holder.statusButton.setEnabled(true);
            holder.bookImage.setImageResource(buku.imageResource);

            holder.statusButton.setOnClickListener(v -> {
                new androidx.appcompat.app.AlertDialog.Builder(ctx)
                        .setTitle("Konfirmasi")
                        .setMessage("Apakah Anda yakin ingin mengembalikan buku \"" + buku.judul + "\"?")
                        .setPositiveButton("Ya", (dialog, which) -> {
                            if (listener != null) {
                                listener.onReturnClick(buku);
                            }
                            new Thread(() -> {
                                lacakDao.updateBookStatus(buku.id, true);  // Set status 'isSelesai' menjadi true
                            }).start();
                        })
                        .setNegativeButton("Batal", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
