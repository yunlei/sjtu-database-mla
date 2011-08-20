package Absyn;

/**
 * @author MaYunlei
 *
 */
public class GrantExp extends Exp {
	public Privileges p;
	public NameList database_list;
	public NameList user_list;
	public boolean WithOption;
	public GrantExp(Privileges p, NameList database_list, NameList user_list,
			boolean withOption) {
		this.p = p;
		this.database_list = database_list;
		this.user_list = user_list;
		this.WithOption = withOption;
	}
	public GrantExp(Privileges p, NameList database_list, NameList user_list) {
		this.p = p;
		this.database_list = database_list;
		this.user_list = user_list;
		this.WithOption =false;
	}
	
}
