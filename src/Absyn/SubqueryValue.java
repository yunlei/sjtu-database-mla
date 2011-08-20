package Absyn;

/**
 * @author MaYunlei
 *
 */
public class SubqueryValue extends Value{
	public SelectExp select;
	public SubqueryValue(SelectExp s)
	{
		select=s;
	}
}
