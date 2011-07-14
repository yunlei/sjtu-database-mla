package Absyn;

 

public class InExp extends BoolExp{
	public Value value1;
	public boolean in;
	public SelectExp select;
	
	public InExp( boolean f,Value v1, SelectExp s){
		value1 = v1;
		select=s;
		in=f;
	}
}
