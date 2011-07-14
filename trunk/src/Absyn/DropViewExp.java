package Absyn;

import Symbol.Symbol;

public class DropViewExp extends DropExp {
	Symbol name;
	public DropViewExp(int p,Symbol s)
	{
		this.pos=p;
		name=s;
	}

}
