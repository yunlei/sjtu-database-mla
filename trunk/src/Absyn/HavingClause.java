package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class HavingClause implements Serializable{
	public BoolExp boolexp;
	
	public HavingClause(BoolExp be){
		boolexp = be;
	}
}
