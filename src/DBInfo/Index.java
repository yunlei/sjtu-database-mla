package DBInfo;

import java.io.Serializable;

public class Index implements Serializable{
	public String db;
	public String table;
	public String col;
	public Index(String db, String table, String col) {
		this.db = db;
		this.table = table;
		this.col = col;
	}
}