package ifp.mobile.projek;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Books.class}, version = 2) // Tingkatkan versi menjadi 2
public abstract class BooksDatabase extends RoomDatabase {
    public abstract BooksDAO booksDao();

    // Tambahkan migrasi
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Contoh migrasi: Menambah kolom baru di tabel 'books'
            database.execSQL("ALTER TABLE books ADD COLUMN new_column INTEGER DEFAULT 0");
        }
    };
}




