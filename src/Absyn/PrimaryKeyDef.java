package Absyn;

/**
 * @author MaYunlei
 *
 */
public class PrimaryKeyDef extends CreateElement {
	public NameList keylist;

	public PrimaryKeyDef(NameList keylist) {
		this.keylist = keylist;
	}
	
}
