package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class ConstValueList implements Serializable{
	public ConstValue value;
	public ConstValueList next;
	public ConstValueList(ConstValue v,ConstValueList n)
	{
		value=v;
		next=n;
	}
}
