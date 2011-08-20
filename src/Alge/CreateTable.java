package Alge;

public class CreateTable extends Relation {
	String database;
	String tableName;
	AttrList attrs;
	public CreateTable(String database, String tableName, AttrList attrs) {
		this.database = database;
		this.tableName = tableName;
		this.attrs = attrs;
	}
	
	
}
