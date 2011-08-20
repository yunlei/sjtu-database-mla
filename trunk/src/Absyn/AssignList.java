package Absyn;

/**
 * @author MaYunlei
 *
 */
public class AssignList {
	public Assignment first;
	public AssignList next;
	public AssignList(Assignment f,AssignList n)
	{
		first=f;
		next=n;
	}
}
