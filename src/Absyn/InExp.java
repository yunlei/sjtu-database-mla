package Absyn;

 

public class InExp extends BoolExp{
	public Value value;
	public boolean in;
	public SelectExp select;
	
	public InExp( boolean f,Value v1, SelectExp s){
		value = v1;
		select=s;
		in=f;
	}
}