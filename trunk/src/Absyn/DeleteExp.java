package Absyn;

import Symbol.Symbol;

public class DeleteExp extends Exp{
	public Symbol name;
	public BoolExp exp;
	public DeleteExp(int p,Symbol n,BoolExp e)
	{
		this.pos=p;
		name=n;
		exp=e;
	}
}
