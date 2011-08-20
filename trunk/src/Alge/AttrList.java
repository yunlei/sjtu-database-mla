package Alge;

import java.io.Serializable;

public class AttrList  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Attr attr;
	public AttrList next;
	public AttrList(Attr attr, AttrList next) {
		this.attr = attr;
		this.next = next;
	}	
	
}
