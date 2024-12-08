package ifp.mobile.projek;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface LacakDao {

    @Query("SELECT * FROM pinjam")
    List<ListBukuPinjam> getAllBooks();

    @Query("SELECT * FROM pinjam WHERE status = 1")
    List<ListBukuPinjam> getFinishedBooks();


    @Query("SELECT * FROM pinjam WHERE status = 0")
    List<ListBukuPinjam> getUnfinishedBooks();

    @Query("UPDATE pinjam SET status = :status WHERE id = :bookId")
    void updateBookStatus(int bookId, boolean status);

    @Insert
    void insertAll(ListBukuPinjam... books);
}
