package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class BoolExsitExp extends BoolExp implements Serializable{
	public boolean exsits;
	public SelectExp select;
	public BoolExsitExp(boolean f,SelectExp s)
	{
		exsits=f;
		select=s;
	}
}
