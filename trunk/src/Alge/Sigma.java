package Alge;

import Absyn.ColName;

public class Sigma extends Relation{
	public Condition condition;
	public Relation relation;

	public Sigma(Condition condition, Relation relation) {
		this.condition = condition;
		this.relation = relation;
	}
	
	
}
