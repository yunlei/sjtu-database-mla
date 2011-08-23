package Alge;
/**
 * 
 * @author MaYunlei
 *多个关系完全链接
 */
public class CrossJoin extends Relation{
	public String tablename; 
	public String asname;
	public Relation leftR;
	public CrossJoin next;
	public CrossJoin(String tablename,Relation leftR, CrossJoin rightR) {
		this.tablename=tablename;
		this.leftR = leftR;
		this.next = rightR;
		asname=leftR.asname;
		// merge attrlist

	}
}