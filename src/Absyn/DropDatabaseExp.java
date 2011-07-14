package Absyn;

import Symbol.Symbol;

public class DropDatabaseExp extends DropExp {
	Symbol name;
	public DropDatabaseExp(int p,Symbol s)
	{
		this.pos=p;
		name=s;
	}
}
