package Alge;

import Absyn.BoolExp;

 
public class Delete extends Relation{
	public String name;
	public BoolExp exp;
	public Delete(String name, BoolExp exp) {
		this.name = name;
		this.exp = exp;
	} 
}
