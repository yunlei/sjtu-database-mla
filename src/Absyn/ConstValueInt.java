package Absyn;

public class ConstValueInt  extends ConstValue{
	/**
	 * 
	 */
	public  int value;
	public ConstValueInt(int p,int value)
	{
		this.pos=p;
		this.value=value;
	}
	public ConstValueInt(int value)
	{
		 
		this.value=value;
	}
	public Integer getValue()
	{
		return value;
	}
}
