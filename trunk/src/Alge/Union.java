package Alge;

public class Union extends Relation{
	public Relation leftR;
	public Relation rightR;
	public Union(Relation leftR, Relation rightR) {
		this.leftR = leftR;
		this.rightR = rightR;
		// merge attrlist
		AttrList llist = null,rlist = null;
		if(leftR!=null&&leftR.attrlist!=null)
			llist=(AttrList) common.Op.copy(leftR.attrlist);
		if(rightR!=null&&rightR.attrlist!=null)
			rlist=(AttrList) common.Op.copy(rightR.attrlist);
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
