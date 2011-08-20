package Absyn;

/**
 * @author MaYunlei
 *
 */
public class BoolOrExp extends BoolExp{
	public BoolExp exp1;
	public BoolExp exp2;
	public BoolOrExp(BoolExp e1,BoolExp e2)
	{
		exp1=e1;
		exp2=e2;
	}
}
