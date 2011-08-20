package Alge;
 
public class CreateIndex extends Relation{
	public String index_name;
	public String  table_name;
	public String col_name;
	public boolean unique;
	public CreateIndex(String index_name, String table_name, String col_name,
			boolean unique) {
		this.index_name = index_name;
		this.table_name = table_name;
		this.col_name = col_name;
		this.unique = unique;
	}
	
	
}
