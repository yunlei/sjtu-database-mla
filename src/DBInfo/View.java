package DBInfo;

import java.io.Serializable;

import Absyn.SelectExp;

public class View implements Serializable{
	public String name;
	public Absyn.SelectExp select;
	public View(String name, SelectExp select) {
		this.name = name;
		this.select = select;
	}
}
