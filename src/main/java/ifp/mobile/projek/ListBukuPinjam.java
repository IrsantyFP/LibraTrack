package ifp.mobile.projek;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pinjam")
public class ListBukuPinjam {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "judul")
    public String judul;

    @ColumnInfo(name ="jatuh tempo")
    public String jatuhTempo;

    @ColumnInfo(name = "gambar")
    public int imageResource;

    @ColumnInfo(name = "status")
    public boolean isSelesai;
    public ListBukuPinjam(){}
    public ListBukuPinjam(int id, String judul, int imageResource) {
        this.id = id;
        this.judul = judul;
        this.imageResource = imageResource;
    }

}


