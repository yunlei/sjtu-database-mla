package Absyn;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class CreateViewExp extends CreateExp {
	public Symbol name;
	public SelectExp select;
	public CreateViewExp(int p, Symbol n, SelectExp s)
	{
		this.pos=p;
		this.name=n;
		this.select=s;
		
	}
}
