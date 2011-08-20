package Alge;
/**
 * 
 * @author MaYunlei
 *多个关系的无关联组合
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
		AttrList llist = null,rlist = null;
		if(leftR!=null&&leftR.attrlist!=null)
			llist=(AttrList) common.Op.copy(leftR.attrlist);
		if(rightR!=null&&rightR.attrlist!=null)
			rlist=(AttrList) common.Op.copy(next.attrlist);
		this.attrlist=llist;
		if(this.attrlist==null)
			this.attrlist=rlist;
		else{
			AttrList tmplist=this.attrlist;
			while(tmplist.next!=null)
				tmplist=tmplist.next;
			tmplist.next=rlist;
		}
	}
}