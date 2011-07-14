package Absyn;

import Symbol.Symbol;

public class OperValue extends Value{

	public Symbol op;
	public Value v1;
	public Value v2;
	public OperValue(Symbol p,Value v1,Value v2)
	{
		op=p;
		this.v1=v1;
		this.v2=v2;
	}
	
}
