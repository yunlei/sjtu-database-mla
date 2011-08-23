package Alge;

import Absyn.AssignList;
import Absyn.BoolExp;

public class Update extends Relation {
	public String tablename;
	public AssignList assign;
	public BoolExp bool;
	public Update(String tablename, AssignList assign, BoolExp bool) {
		this.tablename = tablename;
		this.assign = assign;
		this.bool = bool;
	}
	 
}
