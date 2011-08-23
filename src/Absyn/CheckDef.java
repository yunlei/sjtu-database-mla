package Absyn;

import java.io.Serializable;


/**
 * @author MaYunlei
 *
 */
public class CheckDef extends CreateElement implements Serializable{
	public BoolExp boolexp;
	
	public CheckDef(BoolExp be){
		boolexp = be;
	}
}
