package Absyn;

import Symbol.Symbol;

public class UpdateExp extends Exp{
	public Symbol name;
	public AssignList assign;
	public BoolExp bool;
	public UpdateExp(int p, Symbol n,AssignList list,BoolExp bool )
	{
		this.pos=p;
		name=n;
		assign=list;
		this.bool=bool;
	}
}
