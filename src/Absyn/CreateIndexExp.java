package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * CREATE INDEX INDEX_NAME ON TABLE_NAME (COLIUM_NAME);
 * @author MaYunlei
 *
 */
 
public class CreateIndexExp extends CreateExp implements Serializable{

	private Symbol index_name;
	private Symbol table_name;
	private Symbol col_name;
	private boolean unique;
	public CreateIndexExp(int p,Symbol index_name, Symbol table_name,
			Symbol col_name, boolean unique) {
		this.pos=p;
		this.index_name = index_name;
		this.table_name = table_name;
		this.col_name = col_name;
		this.unique = unique;
	}
	public Symbol getIndex_name() {
		return index_name;
	}
	public Symbol getTable_name() {
		return table_name;
	}
	public Symbol getCol_name() {
		return col_name;
	}
	public boolean isUnique() {
		return unique;
	}
	
}
