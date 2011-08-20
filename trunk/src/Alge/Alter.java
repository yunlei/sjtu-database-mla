package Alge;

public class Alter extends Relation{
	public static int ADD=1;
	public static int DROP=2;
	public int choice;
	public String tablename;
	public String colname;
	public Type type;
	public Alter(int choice, String tablename, String colname) {
		this.choice = choice;
		this.tablename = tablename;
		this.colname = colname;
	}
	public Alter(int choice, String tablename, String colname, Type type) {
		this.choice = choice;
		this.tablename = tablename;
		this.colname = colname;
		this.type = type;
	}
	
}
