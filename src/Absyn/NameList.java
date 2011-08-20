package Absyn;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class NameList {
	public int pos;
	public Symbol name;
	public NameList next;
	public NameList(int p, Symbol s,NameList n)
	{
		pos=p;
		name=s;
		next=n;
	}
}
