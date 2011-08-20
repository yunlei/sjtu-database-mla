package Absyn;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class AlterDropExp extends AlterExp {
	public Symbol tablename;
	public Symbol col;
 
	public AlterDropExp(int p,Symbol tn,Symbol c)
	{
		this.pos=p;
		col=c;
		tablename=tn;col=c;
		
	}
}
