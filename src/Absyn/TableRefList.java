package Absyn;

public class TableRefList {
	public TableRef tableref;
	public TableRefList next;
	public TableRefList(TableRef t,TableRefList n){
		tableref=t;next=n;
	}
}
