package Absyn;

import Symbol.Symbol;

public class CompBoolExp extends BoolExp{
	public Value v1;
	public Symbol comp;
	public Value v2;
	public CompBoolExp(Value v1,Symbol cop,Value v2)
	{
		this.v1=v1;
		this.comp=cop;
		this.v2=v2;
	}
}
