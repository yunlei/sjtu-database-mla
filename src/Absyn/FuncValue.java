package Absyn;

import Symbol.Symbol;

public class FuncValue extends Value{
	public Symbol functy; /*MIN MAX AVG COUNT SUM*/
	public ColName colname;
	
	public FuncValue(Symbol ft, ColName cn){
		functy = ft;
		colname = cn;
	}
}