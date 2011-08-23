package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class PrimaryKeyDef extends CreateElement implements Serializable{
	public NameList keylist;

	public PrimaryKeyDef(NameList keylist) {
		this.keylist = keylist;
	}
	
}
