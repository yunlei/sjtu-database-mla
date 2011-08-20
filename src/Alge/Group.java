package Alge;

import Absyn.ColName;

public class Group extends Relation{
	Relation sub;
	ColName col_name;
	public Group(Relation sub, ColName col_name) {
		this.sub = sub;
		this.col_name = col_name;
	}
	
}
