package ifp.mobile.projek;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class TrackFragment extends Fragment {

    private RecyclerView rvListBukuPinjam;
    private TrackAdapter ongoingAdapter, completedAdapter;
    private List<ListBukuPinjam> ongoingList = new ArrayList<>();
    private List<ListBukuPinjam> completedList = new ArrayList<>();
    private Button btBerlangsung, btSelesai;
    private LacakDao lacakDao;

    public TrackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lacak, container, false);

        rvListBukuPinjam = view.findViewById(R.id.rvListBukuPinjam);
        btBerlangsung = view.findViewById(R.id.btBerlangsung);
        btSelesai = view.findViewById(R.id.btSelesai);

        rvListBukuPinjam.setLayoutManager(new LinearLayoutManager(requireContext()));

        LacakDatabase db = LacakDatabase.getInstance(requireContext());
        lacakDao = db.lacakDao();

        // Ambil data dari database
        loadBooksFromDatabase();

        ongoingAdapter = new TrackAdapter(requireContext(), ongoingList, false);
        completedAdapter = new TrackAdapter(requireContext(), completedList, true);

        // Mengatur click listener pada item
        ongoingAdapter.setOnItemClickListener(buku -> {
            // Pindahkan buku dari ongoing ke completed
            completedList.add(buku);
            ongoingList.remove(buku);

            ongoingAdapter.notifyDataSetChanged();
            completedAdapter.notifyDataSetChanged();

            // Update status buku di database
            new Thread(() -> {
                lacakDao.updateBookStatus(buku.id, true);  // Update status selesai menjadi true
            }).start();
        });

        // Set default adapter to ongoing
        rvListBukuPinjam.setAdapter(ongoingAdapter);

        btBerlangsung.setOnClickListener(v -> switchToOngoing());
        btSelesai.setOnClickListener(v -> switchToCompleted());

        return view;
    }

    private void loadBooksFromDatabase() {
        // Ambil data buku yang sedang berlangsung dan selesai dari database
        new Thread(() -> {
            ongoingList.clear();
            completedList.clear();

            // Mendapatkan daftar buku yang belum selesai (status = 0)
            ongoingList.addAll(lacakDao.getUnfinishedBooks());

            // Mendapatkan daftar buku yang sudah selesai (status = 1)
            completedList.addAll(lacakDao.getFinishedBooks());

            // Update UI di thread utama
            requireActivity().runOnUiThread(() -> {
                ongoingAdapter.notifyDataSetChanged();
                completedAdapter.notifyDataSetChanged();
            });
        }).start();
    }


    private void switchToOngoing() {
        btBerlangsung.setBackgroundTintList(getResources().getColorStateList(R.color.activeTab, null));
        btBerlangsung.setTextColor(getResources().getColor(android.R.color.white, null));
        btSelesai.setBackgroundTintList(getResources().getColorStateList(R.color.inactiveTab, null));
        btSelesai.setTextColor(getResources().getColor(android.R.color.black, null));
        rvListBukuPinjam.setAdapter(ongoingAdapter);
    }

    private void switchToCompleted() {
        btSelesai.setBackgroundTintList(getResources().getColorStateList(R.color.activeTab, null));
        btSelesai.setTextColor(getResources().getColor(android.R.color.white, null));
        btBerlangsung.setBackgroundTintList(getResources().getColorStateList(R.color.inactiveTab, null));
        btBerlangsung.setTextColor(getResources().getColor(android.R.color.black, null));
        rvListBukuPinjam.setAdapter(completedAdapter);
    }
}