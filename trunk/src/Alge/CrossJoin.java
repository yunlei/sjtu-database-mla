package Alge;
/**
 * 
 * @author MaYunlei
 *多个关系完全链接
 */
public class CrossJoin extends Relation{
	//public String tablename; 
	//public String asname;
	public Relation leftR;
	public CrossJoin next;
	public CrossJoin(Relation leftR, CrossJoin rightR) {
		
		this.leftR = leftR;
		this.next = rightR;
		 
		// merge attrlist

	}
}