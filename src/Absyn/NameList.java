package Absyn;

import Symbol.Symbol;

public class NameList {
	int pos;
	Symbol name;
	NameList next;
	public NameList(int p, Symbol s,NameList n)
	{
		pos=p;
		name=s;
		next=n;
	}
}
