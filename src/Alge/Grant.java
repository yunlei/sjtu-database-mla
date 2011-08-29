package Alge;

import Absyn.NameList;
import Absyn.Privileges;

public class Grant extends Relation{
	public int priority ;
	public NameList table_list;
	public NameList user_list;
	public Grant(int priority, NameList table_list, NameList user_list) {
		this.priority = priority;
		this.table_list = table_list;
		this.user_list = user_list;
	}
 
	
	
}
