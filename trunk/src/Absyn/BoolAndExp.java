package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class BoolAndExp extends BoolExp implements Serializable{
	public BoolExp exp1;
	public BoolExp exp2;
	public BoolAndExp(BoolExp e1,BoolExp e2)
	{
		exp1=e1;
		exp2=e2;
	}
}
