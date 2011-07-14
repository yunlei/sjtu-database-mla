package Absyn;

public class BoolAndExp extends BoolExp{
	BoolExp exp1;
	BoolExp exp2;
	public BoolAndExp(BoolExp e1,BoolExp e2)
	{
		exp1=e1;
		exp2=e2;
	}
}
