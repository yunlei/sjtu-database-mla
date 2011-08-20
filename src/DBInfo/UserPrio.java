package DBInfo;

import java.io.Serializable;

public class UserPrio implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 11L;
	String username;
	String database;
	boolean write;
	boolean read;
	boolean grant;
	public UserPrio(String username, String database, boolean write,
			boolean read, boolean grant) {
		this.username = username;
		this.database = database;
		this.write = write;
		this.read = read;
		this.grant = grant;
	}
	
}
