package Alge;

import Absyn.SelectExp;

public class CreateView extends Relation{
	public String name;
	public Absyn.SelectExp select;
	public CreateView(String name, SelectExp select) {
		this.name = name;
		this.select = select;
	}
}
