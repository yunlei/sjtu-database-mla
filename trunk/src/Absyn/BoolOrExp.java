package Absyn;

public class BoolOrExp extends BoolExp{
	BoolExp exp1;
	BoolExp exp2;
	public BoolOrExp(BoolExp e1,BoolExp e2)
	{
		exp1=e1;
		exp2=e2;
	}
}
