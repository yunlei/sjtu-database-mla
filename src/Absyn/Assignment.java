package Absyn;

import Symbol.Symbol;

public class Assignment {
	int pos;
	public Symbol var;
	public Value value;
	public Assignment(int p,Symbol v,Value va)
	{
		pos=p;
		var=v;
		value=va;
	}
}
