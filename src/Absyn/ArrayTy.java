package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class ArrayTy extends DataType implements Serializable{
	public Symbol ty;
	public int length;
	public ArrayTy(Symbol t,int l)
	{
		ty=t;
		length=l;
	}
}