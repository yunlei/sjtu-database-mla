package Alge;

import Absyn.OrderList;

public class Order extends Relation{
	Relation sub;
	OrderList orderlist;
	public Order(Relation sub, OrderList orderlist) {
		this.sub = sub;
		this.orderlist = orderlist;
	}
	
}
