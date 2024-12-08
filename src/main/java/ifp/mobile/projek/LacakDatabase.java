package ifp.mobile.projek;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ListBukuPinjam.class}, version = 1)
public abstract class LacakDatabase extends RoomDatabase {
    private static LacakDatabase INSTANCE;

    public abstract LacakDao lacakDao();

    // Singleton untuk instance database
    public static synchronized LacakDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LacakDatabase.class, "lacak_database")
                    .fallbackToDestructiveMigration() // Optional: Menghapus data saat ada perubahan schema
                    .build();
        }
        return INSTANCE;
    }

}
