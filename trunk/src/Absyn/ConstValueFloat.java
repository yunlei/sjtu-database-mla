package Absyn;

import java.io.Serializable;

public class ConstValueFloat extends ConstValue implements Serializable{
	/**
	 * 
	 */
	public double value;
	public ConstValueFloat(int pos,double v)
	{
		this.pos=pos;
		this.value=v;
	}
	public ConstValueFloat(double v)
	{ 
		this.value=v;
	}
	public Double getValue()
	{
		return value;
	}
}
