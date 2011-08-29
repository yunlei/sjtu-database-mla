package DBInfo;

import java.io.Serializable;

public class UserPrio implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 11L;
	public String username;
	public String tablename;
	int priority;
	public static int  SELECT=1;
	public static int  INSERT=2;
	public static int  UPDATE=4;
	public static int  GRANT=8;
	public UserPrio(String username, String tablename, int priority) {
		this.username = username;
		this.tablename = tablename;
		this.priority = priority;
	}
	public void addPriority(int priority){
		this.priority |= priority;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public boolean hasPriority(int prio){
		return (this.priority&prio)!=0;
	}
	
}
