package Absyn;

/**
 * @author MaYunlei
 *
 */
public class BoolExsitExp extends BoolExp{
	public boolean exsits;
	public SelectExp select;
	public BoolExsitExp(boolean f,SelectExp s)
	{
		exsits=f;
		select=s;
	}
}
