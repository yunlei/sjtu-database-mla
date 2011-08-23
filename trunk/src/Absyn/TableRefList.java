package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class TableRefList implements Serializable{
	public TableRef tableref;
	public TableRefList next;
	public TableRefList(TableRef t,TableRefList n){
		tableref=t;next=n;
	}
}
