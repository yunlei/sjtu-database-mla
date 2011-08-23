package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class SQLList extends Exp implements Serializable{
	public Exp first;
	public SQLList next;
	public SQLList(Exp first, SQLList next) {
		this.first = first;
		this.next = next;
	}

}
