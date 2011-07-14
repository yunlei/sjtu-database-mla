package Absyn;

public class ConstValueFloat extends ConstValue{
	double value;
	public ConstValueFloat(int pos,double v)
	{
		this.pos=pos;
		this.value=v;
	}
	public ConstValueFloat(double v)
	{
	
		this.value=v;
	}
}
