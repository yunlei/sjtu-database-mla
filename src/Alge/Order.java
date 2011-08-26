package Alge;

import Absyn.OrderList;

public class Order extends Relation{
	public Relation sub;
	public OrderList orderlist;
	public Order(Relation sub, OrderList orderlist) {
		this.sub = sub;
		this.orderlist = orderlist;
	}
	
}
