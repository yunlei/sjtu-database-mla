package Absyn;

public class TableRefList {
	TableRef tableref;
	TableRefList next;
	public TableRefList(TableRef t,TableRefList n){
		tableref=t;next=n;
	}
}
