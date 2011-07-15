package Absyn;

public class ConstValueList {
	public ConstValue value;
	public ConstValueList next;
	public ConstValueList(ConstValue v,ConstValueList n)
	{
		value=v;
		next=n;
	}
}
