package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class ColNameList implements Serializable{
	public ColName name;
	public ColNameList next;
	public ColNameList(ColName name, ColNameList next) {
		this.name = name;
		this.next = next;
	}
	
}
