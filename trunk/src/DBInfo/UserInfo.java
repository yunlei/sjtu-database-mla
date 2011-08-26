package DBInfo;

import java.io.Serializable;

public class UserInfo implements Serializable {
	static int auto_incre_id=0;
	public int id;
	public String username;
	public String password;
	public UserInfo(String username, String password) {
		this.id = auto_incre_id++;
		this.username = username;
		this.password = password;
	} 
	
}
