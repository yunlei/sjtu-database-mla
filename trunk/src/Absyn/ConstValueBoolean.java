package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class ConstValueBoolean extends ConstValue implements Serializable{
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
	public String getValue()
	{
		if(flag)
			return "true";
		return "false";
	}
	
}
