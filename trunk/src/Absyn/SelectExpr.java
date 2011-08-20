package Absyn;

/**
 * @author MaYunlei
 *
 */
public class SelectExpr {
	public Value value;
	public AsOrNot alias;
	public SelectExpr next;
	public SelectExpr(Value v,AsOrNot a,SelectExpr n){
		value=v;
		alias=a;
		next=n;
	}
}
