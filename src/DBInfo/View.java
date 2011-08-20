package DBInfo;

import Absyn.SelectExp;

public class View {
	String name;
	Absyn.SelectExp select;
	public View(String name, SelectExp select) {
		this.name = name;
		this.select = select;
	}
}
