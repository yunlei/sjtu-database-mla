package Absyn;

public class ConstValueBoolean extends ConstValue{
	public boolean flag;
	public ConstValueBoolean(int p,boolean f)
	{
		this.pos=p;
		flag=f;
	}
	public ConstValueBoolean(boolean f)
	{
		flag=f;
	}
}
