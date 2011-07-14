package Absyn;

import Symbol.Symbol;

/**
 * CREATE INDEX INDEX_NAME ON TABLE_NAME (COLIUM_NAME);
 * @author MaYunlei
 *
 */
public class CreateIndexExp extends CreateExp {

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
	
}
