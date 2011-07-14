package Absyn;

import Symbol.Symbol;

public class CreateViewExp extends CreateExp {
	private Symbol name;
	private SelectExp select;
	public CreateViewExp(int p, Symbol n, SelectExp s)
	{
		this.pos=p;
		this.name=n;
		this.select=s;
		
	}
}
