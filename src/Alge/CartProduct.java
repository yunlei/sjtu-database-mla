package Alge;

public class CartProduct extends Relation{
	public Relation leftR;
	public Relation rightR;
	public CartProduct(Relation leftR, Relation rightR) {
		this.leftR = leftR;
		this.rightR = rightR;
	}
	
}
