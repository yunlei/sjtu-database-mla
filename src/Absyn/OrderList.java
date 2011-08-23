package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class OrderList implements Serializable{
	public ColName col;
	Symbol asc_desc;
	public OrderList next;
	public OrderList(ColName c,Symbol ad,OrderList n)
	{
		col=c;
		asc_desc=ad;
		next=n;
	}
}
