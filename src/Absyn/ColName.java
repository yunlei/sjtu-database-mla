package Absyn;

import Symbol.Symbol;

public class ColName {
	public Symbol table;
	public Symbol col;
	public ColName(Symbol t,Symbol c)
	{
		table=t;
		col=c;
	}
}
