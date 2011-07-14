package Absyn;

public class AssignList {
	Assignment first;
	AssignList next;
	public AssignList(Assignment f,AssignList n)
	{
		first=f;
		next=n;
	}
}
