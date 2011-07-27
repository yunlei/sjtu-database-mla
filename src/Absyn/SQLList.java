package Absyn;

public class SQLList extends Exp{
	public Exp first;
	public SQLList next;
	public SQLList(Exp first, SQLList next) {
		this.first = first;
		this.next = next;
	}

}
