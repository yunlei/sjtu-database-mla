package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class Privileges implements Serializable{
	public Privilege Privilege;
	public Privileges next;
	public Privileges( Privilege privilege, Privileges next) {
		Privilege = privilege;
		this.next = next;
	}
	
}
