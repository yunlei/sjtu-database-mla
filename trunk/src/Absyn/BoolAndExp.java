package Absyn;

public class BoolAndExp extends BoolExp{
	public BoolExp exp1;
	public BoolExp exp2;
	public BoolAndExp(BoolExp e1,BoolExp e2)
	{
		exp1=e1;
		exp2=e2;
	}
}
