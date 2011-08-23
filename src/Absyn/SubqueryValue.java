package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class SubqueryValue extends Value implements Serializable{
	public SelectExp select;
	public SubqueryValue(SelectExp s)
	{
		select=s;
	}
}
