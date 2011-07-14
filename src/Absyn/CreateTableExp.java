package Absyn;

import Symbol.Symbol;

public class CreateTableExp extends CreateExp{
	private Symbol name;
	private CreateElementList element;
	public CreateTableExp(int p,Symbol name, CreateElementList element) {
		this.pos=p;
		this.name = name;
		this.element = element;
	}
	
}
