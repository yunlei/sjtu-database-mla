package Absyn;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class DropViewExp extends DropExp {
	public Symbol name;
	public DropViewExp(int p,Symbol s)
	{
		this.pos=p;
		name=s;
	}

}
