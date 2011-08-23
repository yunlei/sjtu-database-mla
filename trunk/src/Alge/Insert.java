package Alge;

import Absyn.ConstValueList;
import Absyn.NameList;

public class Insert extends Relation{
	public String tablename;
	public NameList namelist;
	public ConstValueList constvalue;
	public Relation select;
	public Insert(String tablename,NameList namelist, ConstValueList constvalue) {
		this.tablename=tablename;
		this.namelist = namelist;
		this.constvalue = constvalue;
		this.select = null;
	}
	public Insert(String tablename,NameList namelist, Relation select) {
		this.tablename=tablename;
		this.namelist = namelist;
		this.select = select;
		this.constvalue =null;
	}
	
}
