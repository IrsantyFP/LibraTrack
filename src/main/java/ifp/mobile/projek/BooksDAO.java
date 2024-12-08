package ifp.mobile.projek;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BooksDAO {
    @Query("SELECT * FROM Books")
    List<Books> getAllBooks();

    @Insert
    void insertAll(Books... books);

    @Delete
    void delete(Books books);

    @Query("DELETE FROM Books")
    void deleteAllBooks();
}
