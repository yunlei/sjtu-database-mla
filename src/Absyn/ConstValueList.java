package Absyn;

public class ConstValueList {
	ConstValue value;
	ConstValueList next;
	public ConstValueList(ConstValue v,ConstValueList n)
	{
		value=v;
		next=n;
	}
}
