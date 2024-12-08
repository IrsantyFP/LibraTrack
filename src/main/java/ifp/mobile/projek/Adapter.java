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

public class Adapter extends RecyclerView.Adapter<Adapter.bukuVH> {

    private final Context ctx;
    private List<ListBuku> data;

    public Adapter(Context ctx, List<ListBuku> data) {
        this.ctx = ctx;
        this.data = data;
    }

    public class bukuVH extends RecyclerView.ViewHolder {
        private final TextView tvJudul;
        private final TextView tvPenulis;
        private final TextView tvTahun;
        private final Button btHapus; // Tombol Hapus
        private final ImageView ivCover;

        public bukuVH(@NonNull View itemView) {
            super(itemView);
            this.tvJudul = itemView.findViewById(R.id.tvJudul);
            this.tvPenulis = itemView.findViewById(R.id.tvPenulis);
            this.tvTahun = itemView.findViewById(R.id.tvTahun);
            this.btHapus = itemView.findViewById(R.id.btHapus);
            this.ivCover = itemView.findViewById(R.id.ivCover);// Tombol Hapus
        }
    }

    @NonNull
    @Override
    public bukuVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(this.ctx).inflate(R.layout.rowview, parent, false);
        return new bukuVH(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull bukuVH holder, int position) {
        ListBuku b = this.data.get(position);
        holder.tvJudul.setText(b.judul);
        holder.tvPenulis.setText(b.penulis);
        holder.tvTahun.setText(b.tahun);
        holder.ivCover.setImageResource(b.cover);

        // Tambahkan listener untuk tombol Hapus
        holder.btHapus.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();

            // Hapus dari database
            new Thread(() -> {
                BookmarkDatabase db = BookmarkDatabase.getInstance(ctx);
                BookmarkDao bookmarkDao = db.bukuDao();
                bookmarkDao.delete(b);
            }).start();

            // Hapus dari daftar dan perbarui RecyclerView
            data.remove(pos);
            notifyItemRemoved(pos);
        });
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public void setData(List<ListBuku> newData) {
        this.data = newData;
        notifyDataSetChanged(); // Perbarui RecyclerView dengan data baru
    }
}
