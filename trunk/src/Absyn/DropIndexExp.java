package Absyn;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class DropIndexExp extends DropExp {
	public Symbol tablename;
	public Symbol indexname;
	public DropIndexExp(int p,Symbol s1,Symbol s2)
	{
		this.pos=p;
		tablename=s1;
		indexname=s2;
	}
}
