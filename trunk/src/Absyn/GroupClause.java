package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class GroupClause implements Serializable{
	public ColName name;
	public GroupClause(ColName c){
		name=c;
	}
}
