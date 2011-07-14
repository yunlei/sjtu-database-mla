package Absyn;


public class LikeEscapeExp extends BoolExp{
	public Value value;
	public boolean like_or_not;
	public boolean escape_or_not;
	public String likestring;
	public String escapestring;
	
	public LikeEscapeExp(Value v, boolean l, boolean e, String li, String es){
		value = v;
		like_or_not = l;
		escape_or_not = e;
		likestring = li;
		escapestring = es;
	}
}