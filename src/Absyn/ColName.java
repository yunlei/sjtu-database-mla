package Absyn;

import Symbol.Symbol;

public class ColName {
	Symbol table;
	Symbol col;
	public ColName(Symbol t,Symbol c)
	{
		table=t;
		col=c;
	}
}
