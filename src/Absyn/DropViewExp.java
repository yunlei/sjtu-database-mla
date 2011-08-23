package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class DropViewExp extends DropExp implements Serializable{
	public Symbol name;
	public DropViewExp(int p,Symbol s)
	{
		this.pos=p;
		name=s;
	}

}
