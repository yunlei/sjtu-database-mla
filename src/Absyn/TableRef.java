package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class TableRef implements Serializable{
	public Symbol name;
	public Symbol asname;
	public SelectExp subquery;
	public TableRef(Symbol n,Symbol a,SelectExp s)
	{
		name=n;
		asname=a;
		subquery=s;
	}
	
}
