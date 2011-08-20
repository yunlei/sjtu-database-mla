package Alge;

public class Join extends Relation{
	public Relation leftR;
	public Relation rightR;
	public Join(Relation leftR, Relation rightR) {
		this.leftR = leftR;
		this.rightR = rightR;
	}
}
