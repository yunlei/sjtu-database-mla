package Absyn;

public class SubqueryValue extends Value{
	SelectExp select;
	public SubqueryValue(SelectExp s)
	{
		select=s;
	}
}
