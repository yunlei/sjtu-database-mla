package DBInfo;

import java.io.Serializable;

public class PrioList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 21L;
	UserPrio prio;
	PrioList next;
	public PrioList(UserPrio prio, PrioList next) {
		this.prio = prio;
		this.next = next;
	}
	
}
