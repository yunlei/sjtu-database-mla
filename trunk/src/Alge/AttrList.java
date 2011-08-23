package Alge;

import java.io.Serializable;

public class AttrList  implements Serializable{
	/**
	 * 
	 */
	 
	public Attr attr;
	public AttrList next;
	public AttrList(Attr attr, AttrList next) {
		this.attr = attr;
		this.next = next;
	}	
	
}
