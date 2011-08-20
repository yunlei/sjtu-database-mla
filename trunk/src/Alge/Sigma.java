package Alge;

public class Sigma extends Relation{
	Condition condition;
	Relation relation;
	public Sigma(Condition condition, Relation relation) {
		this.condition = condition;
		this.relation = relation;
	}
	
}
