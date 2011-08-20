package Alge;

import Absyn.AssignList;
import Absyn.BoolExp;

public class Update extends Relation {
	public Relation sub;
	public AssignList assign;
	public BoolExp bool;
	public Update(Relation sub, AssignList assign, BoolExp bool) {
		this.sub = sub;
		this.assign = assign;
		this.bool = bool;
	}
	
}
