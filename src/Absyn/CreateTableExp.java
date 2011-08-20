package Absyn;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class CreateTableExp extends CreateExp{
	public Symbol name;
	public CreateElementList element;
	public BoolExp check;
	public CreateTableExp(int p,Symbol name, CreateElementList element) {
		this.pos=p;
		this.name = name;
		this.element = element;
		this.check = null;
	}
	
	public CreateTableExp(int p,Symbol name, CreateElementList element, BoolExp check) {
		this.pos=p;
		this.name = name;
		this.element = element;
		this.check = check;
	}

	public Symbol getName() {
		return name;
	}
	public CreateElementList getElement() {
		return element;
	}
	
}
