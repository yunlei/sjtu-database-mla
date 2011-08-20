package Absyn;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class AnyExp extends BoolExp{
	public Value value;
	public Symbol comp; /* =  <  >  >=  <=  <>*/
	public SelectExp select;
	
	public AnyExp(Value v1, Symbol cop, SelectExp s){
		value = v1;
		select=s;
		comp = cop;
	}
}
