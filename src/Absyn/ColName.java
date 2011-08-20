package Absyn;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class ColName {
	public Symbol table;
	public Symbol col;
	public ColName(Symbol t,Symbol c)
	{
		table=t;
		col=c;
	}
	public ColName(String s, String s2) {
		// TODO Auto-generated constructor stub
		table=new Symbol(s);
		col=new Symbol(s2);
	}
}
