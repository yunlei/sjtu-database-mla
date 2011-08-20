package Absyn;

/**
 * @author MaYunlei
 *
 */
public class ColNameList {
	public ColName name;
	public ColNameList next;
	public ColNameList(ColName name, ColNameList next) {
		this.name = name;
		this.next = next;
	}
	
}
