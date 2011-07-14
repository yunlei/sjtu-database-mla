package Absyn;

import Symbol.Symbol;

public class DropIndexExp extends DropExp {
	Symbol tablename;
	Symbol indexname;
	public DropIndexExp(int p,Symbol s1,Symbol s2)
	{
		this.pos=p;
		tablename=s1;
		indexname=s2;
	}
}
