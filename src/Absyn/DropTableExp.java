package Absyn;

import Symbol.Symbol;

public class DropTableExp extends DropExp{
	Symbol name;
	public DropTableExp(int p, Symbol symbol)
	{
		this.pos=p;
		name=symbol;
	}
}
