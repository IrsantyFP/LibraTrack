package ifp.mobile.projek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context context;
    private List<Books> booksList;
    private OnItemClickListener listener;

    // Konstruktor untuk menerima listener
    public BookAdapter(Context context, List<Books> booksList, OnItemClickListener listener) {
        this.context = context;
        this.booksList = booksList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_rowview, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Books book = booksList.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView title, author;
        private ImageView imageView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            imageView = itemView.findViewById(R.id.bookCover);

            itemView.setOnClickListener(v -> {
                // Saat item ditekan, panggil listener
                listener.onItemClick(getAdapterPosition());
            });
        }

        public void bind(Books book) {
            title.setText(book.title);
            author.setText(book.author);
            Glide.with(context).load(book.image).into(imageView);
        }
    }

    // Interface untuk klik listener
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
