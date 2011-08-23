package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class DeleteExp extends Exp implements Serializable{
	public Symbol name;
	public BoolExp exp;
	public DeleteExp(int p,Symbol n,BoolExp e)
	{
		this.pos=p;
		name=n;
		exp=e;
	}
}
