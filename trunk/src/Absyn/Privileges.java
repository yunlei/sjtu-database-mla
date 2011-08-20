package Absyn;

/**
 * @author MaYunlei
 *
 */
public class Privileges {
	public Privilege Privilege;
	public Privileges next;
	public Privileges( Privilege privilege, Privileges next) {
		Privilege = privilege;
		this.next = next;
	}
	
}
