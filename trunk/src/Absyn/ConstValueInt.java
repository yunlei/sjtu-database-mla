package Absyn;

import java.io.Serializable;

public class ConstValueInt  extends ConstValue implements Serializable{
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
