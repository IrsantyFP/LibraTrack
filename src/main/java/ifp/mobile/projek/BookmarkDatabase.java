package ifp.mobile.projek;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ListBuku.class}, version = 1)
public abstract class BookmarkDatabase extends RoomDatabase {

    private static volatile BookmarkDatabase INSTANCE;

    public static synchronized BookmarkDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BookmarkDatabase.class, "bookmark_database")
                    .fallbackToDestructiveMigration() // Optional: Menghapus data saat ada perubahan schema
                    .build();
        }
        return INSTANCE;
    }
    public abstract BookmarkDao bukuDao();
}