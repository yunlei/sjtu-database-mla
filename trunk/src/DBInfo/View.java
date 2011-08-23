package DBInfo;

import Absyn.SelectExp;

public class View {
	public String name;
	public Absyn.SelectExp select;
	public View(String name, SelectExp select) {
		this.name = name;
		this.select = select;
	}
}
