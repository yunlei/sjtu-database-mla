package Absyn;

public class ConstValueInt  extends ConstValue{
	int value;
	public ConstValueInt(int p,int value)
	{
		this.pos=p;
		this.value=value;
	}
	public ConstValueInt(int value)
	{
		 
		this.value=value;
	}
}
