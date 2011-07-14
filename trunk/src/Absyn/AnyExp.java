package Absyn;

import Symbol.Symbol;

public class AnyExp extends BoolExp{
	public Value value1;
	public Symbol comp; /* =  <  >  >=  <=  <>*/
	public SelectExp select;
	
	public AnyExp(Value v1, Symbol cop, SelectExp s){
		value1 = v1;
		select=s;
		comp = cop;
	}
}
