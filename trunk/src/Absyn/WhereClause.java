package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class WhereClause implements Serializable{
	public BoolExp boolexp;
	public WhereClause(BoolExp  b)
	{
		boolexp=b;
	}
}
