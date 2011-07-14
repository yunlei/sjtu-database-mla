package Absyn;

import Symbol.Symbol;

public class OrderList {
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
