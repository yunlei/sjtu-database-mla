package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class AssignList implements Serializable{
	public Assignment first;
	public AssignList next;
	public AssignList(Assignment f,AssignList n)
	{
		first=f;
		next=n;
	}
}
