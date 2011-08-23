package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class Privilege implements Serializable{

	public Symbol privilege;

	public Privilege(Symbol privilege) {
		this.privilege = privilege;
	}
	
}
