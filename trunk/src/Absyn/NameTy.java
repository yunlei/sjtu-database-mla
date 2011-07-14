package Absyn;

import Symbol.Symbol;

public class NameTy extends DataType{
	Symbol ty;
	public NameTy(Symbol t)
	{
		ty=t;
	}
}
