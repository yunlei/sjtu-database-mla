package Alge;

import Absyn.SelectExpr;

public class Project extends Relation{
	public Absyn.SelectExpr select_expr;
	public Relation sub;
	public Project(SelectExpr select_expr, Relation sub) {
		this.select_expr = select_expr;
		this.sub = sub;
	}
	
}
