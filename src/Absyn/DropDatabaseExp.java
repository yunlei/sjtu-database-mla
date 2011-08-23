package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class DropDatabaseExp extends DropExp implements Serializable{
	public Symbol name;
	public DropDatabaseExp(int p,Symbol s)
	{
		this.pos=p;
		name=s;
	}
}
