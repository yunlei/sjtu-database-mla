package Absyn;

import Symbol.Symbol;

public class ColumnDefinition extends CreateElement {
	int pos;
	public Symbol name;
	public DataType type;
	public Symbol key_or_not;
	public Symbol null_or_not;  /*NULL null*/
	public Symbol auto_increment_or_not; /* AUTO null*/
	public ConstValue defaultvalue;
	public BoolExp check_exp;
	public ColumnDefinition(int p, Symbol n, DataType dty, BoolExp b){
		pos = p;
		name = n;
		type = dty;
		check_exp = b;
	}
	
	public ColumnDefinition(int p,
							Symbol n,
							DataType ty,
							Symbol key_o_n,
							Symbol null_o_n,
							Symbol auto_i,
							ConstValue dv){
		pos = p;
		name = n;
		type = ty;
		key_or_not = key_o_n;
		null_or_not = null_o_n;
		auto_increment_or_not = auto_i;
		defaultvalue = dv;
		check_exp = null;
	}
}
