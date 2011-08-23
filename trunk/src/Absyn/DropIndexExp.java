package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class DropIndexExp extends DropExp implements Serializable{
	public Symbol tablename;
	public Symbol indexname;
	public DropIndexExp(int p,Symbol s1,Symbol s2)
	{
		this.pos=p;
		tablename=s1;
		indexname=s2;
	}
}
