package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class OperValue extends Value implements Serializable{

	public Symbol op;
	public Value v1;
	public Value v2;
	public OperValue(Symbol p,Value v1,Value v2)
	{
		op=p;
		this.v1=v1;
		this.v2=v2;
	}
	
}
