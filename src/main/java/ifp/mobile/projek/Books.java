package ifp.mobile.projek;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Books")
public class Books {
	@PrimaryKey(autoGenerate = true)
	public int id; // Auto-generate ID

	public String title;
	public String author;

	@ColumnInfo(name = "status")
	public String status;

	@ColumnInfo(name = "borrow_date")
	public String borrowDate;

	@ColumnInfo(name = "return_date")
	public String returnDate;

	@ColumnInfo(name = "image") // Menyimpan path gambar
	public int image;
}
