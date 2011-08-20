package Absyn;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class DropDatabaseExp extends DropExp {
	public Symbol name;
	public DropDatabaseExp(int p,Symbol s)
	{
		this.pos=p;
		name=s;
	}
}
