package Absyn;

import Symbol.Symbol;

public class AllExp extends BoolExp{
	public Value value;
	public Symbol comp; /* =  <  >  >=  <=  <>*/
	public SelectExp select;
	
	public AllExp(Value v1, Symbol cop, SelectExp s){
		value = v1;
		select=s;
		comp = cop;
	}
}
