package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class GrantExp extends Exp implements Serializable{
	public Privileges p;
	public NameList table_list;
	public NameList user_list;
	public boolean WithOption;
	public GrantExp(Privileges p, NameList database_list, NameList user_list,
			boolean withOption) {
		this.p = p;
		this.table_list = database_list;
		this.user_list = user_list;
		this.WithOption = withOption;
	}
	public GrantExp(Privileges p, NameList database_list, NameList user_list) {
		this.p = p;
		this.table_list = database_list;
		this.user_list = user_list;
		this.WithOption =false;
	}
	
}
