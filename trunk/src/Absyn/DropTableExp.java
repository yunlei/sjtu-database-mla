package Absyn;

import Symbol.Symbol;

public class DropTableExp extends DropExp{
	public Symbol name;
	public DropTableExp(int p, Symbol symbol)
	{
		this.pos=p;
		name=symbol;
	}
}
