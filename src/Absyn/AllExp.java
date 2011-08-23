package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class AllExp extends BoolExp implements Serializable{
	public Value value;
	public Symbol comp; /* =  <  >  >=  <=  <>*/
	public SelectExp select;
	
	public AllExp(Value v1, Symbol cop, SelectExp s){
		value = v1;
		select=s;
		comp = cop;
	}
}
