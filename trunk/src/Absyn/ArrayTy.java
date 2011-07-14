package Absyn;

import Symbol.Symbol;

public class ArrayTy extends DataType{
	public Symbol ty;
	int length;
	public ArrayTy(Symbol t,int l)
	{
		ty=t;
		length=l;
	}
}