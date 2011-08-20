package Alge;

public class ThetaJoin extends Relation{
	public Relation leftR;
	public Relation rightR;
	public ThetaJoin(Relation leftR, Relation rightR) {
		this.leftR = leftR;
		this.rightR = rightR;
	}
	
}
