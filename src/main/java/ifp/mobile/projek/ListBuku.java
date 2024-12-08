package ifp.mobile.projek;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ListBuku {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "judul")
    public String judul;

    @ColumnInfo(name = "penulis")
    public String penulis;

    @ColumnInfo(name = "tahun")
    public String tahun;

    @ColumnInfo(name = "cover")
    public int cover;
}

