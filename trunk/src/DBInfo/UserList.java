package DBInfo;

public class UserList {
	public UserInfo user;
	public UserList next;
	public UserList(UserInfo user, UserList next) {
		this.user = user;
		this.next = next;
	}
	
}
