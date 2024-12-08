package ifp.mobile.projek;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookmarkDao {

    @Query("SELECT * FROM ListBuku")
    List<ListBuku> getAllListBuku();


    @Query("SELECT * FROM ListBuku WHERE id = :id")
    ListBuku getListBukuById(int id);

    @Query("SELECT * FROM ListBuku WHERE judul = :judul")
    ListBuku getListBukuByJudul(String judul);

    @Query("SELECT * FROM ListBuku WHERE penulis = :penulis")
    ListBuku getListBukuByPenulis(String penulis);

    @Query("SELECT * FROM ListBuku WHERE tahun = :tahun")
    ListBuku getListBukuByTahun(String tahun);

    @Insert
    void insertAll(ListBuku... listBuku);

    @Delete
    void delete(ListBuku listBuku);
}
