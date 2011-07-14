package Absyn;

import Symbol.Symbol;

public class TableRef {
	public Symbol name;
	public Symbol asname;
	public SelectExp subquery;
	public TableRef(Symbol n,Symbol a,SelectExp s)
	{
		name=n;
		asname=a;
		subquery=s;
	}
	
}
