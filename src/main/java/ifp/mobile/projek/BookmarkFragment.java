package ifp.mobile.projek;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class BookmarkFragment extends Fragment {

    private RecyclerView rvListBuku;
    private List<ListBuku> data;
    private Adapter adapter;
    private Handler handler;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_bookmark, container, false);
        this.rvListBuku = v.findViewById(R.id.rvListBuku);

        this.handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                adapter.notifyDataSetChanged();
            }
        };

        this.data = new ArrayList<>();
        this.adapter = new Adapter(requireContext(), this.data);
        this.rvListBuku.setAdapter(this.adapter);
        this.rvListBuku.setLayoutManager(new LinearLayoutManager(requireContext()));


        Thread t = new Thread(() -> {
            BookmarkDatabase db = BookmarkDatabase.getInstance(getContext());
            BookmarkDao bukuDao = db.bukuDao();
            List<ListBuku> bukuList = bukuDao.getAllListBuku();

           requireActivity().runOnUiThread(() -> {
               this.data.clear();
               this.data.addAll(bukuList);
               this.adapter.notifyDataSetChanged();
           });
        });
        t.start();

        return v;
    }


}