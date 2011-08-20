package Alge;

import Absyn.ConstValueList;
import Absyn.NameList;

public class Insert extends Relation{
	public NameList namelist;
	public ConstValueList constvalue;
	public Select select;
	public Insert(NameList namelist, ConstValueList constvalue) {
		this.namelist = namelist;
		this.constvalue = constvalue;
		this.select = null;
	}
	public Insert(NameList namelist, Select select) {
		this.namelist = namelist;
		this.select = select;
		this.constvalue =null;
	}
	
}
