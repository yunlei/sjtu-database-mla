package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class OrderClause implements Serializable{
	public OrderList orderlist;
	public OrderClause(OrderList or)
	{
		orderlist=or;
	}
}
