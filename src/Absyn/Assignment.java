package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class Assignment implements Serializable{
	public int pos;
	public Symbol var;
	public Value value;
	public Assignment(int p,Symbol v,Value va)
	{
		pos=p;
		var=v;
		value=va;
	}
}
