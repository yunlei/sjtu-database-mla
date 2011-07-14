package Absyn;

import Symbol.Symbol;

public class SelectExp extends Exp{
	public Symbol distinct_or_not;
	public SelectExpr selectexpr;
	public FromClause fromclause;
	public WhereClause whereclause;
	public GroupClause groupclause;
	public HavingClause havingclause;
	public OrderClause orderclause;
	
	public SelectExp(Symbol d,
					 SelectExpr s,
					 FromClause f,
					 WhereClause w,
					 GroupClause g,
					 HavingClause h,
					 OrderClause o){
		distinct_or_not = d;
		selectexpr = s;
		fromclause = f;
		whereclause = w;
		groupclause = g;
		havingclause = h;
		orderclause = o;
	}
}
