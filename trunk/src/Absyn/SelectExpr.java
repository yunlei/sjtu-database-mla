package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class SelectExpr implements Serializable{
	public Value value;
	public AsOrNot alias;
	public SelectExpr next;
	public SelectExpr(Value v,AsOrNot a,SelectExpr n){
		value=v;
		alias=a;
		next=n;
	}
}
