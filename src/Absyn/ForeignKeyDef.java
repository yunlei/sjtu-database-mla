package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class ForeignKeyDef extends   CreateElement implements Serializable{
	public int pos;
	public Symbol col;
	public ColName fk;
	public ForeignKeyDef(int p,Symbol col, ColName fk) {
		pos=p;
		this.col = col;
		this.fk = fk;
	}
}
