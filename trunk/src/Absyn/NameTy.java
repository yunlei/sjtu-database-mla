package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class NameTy extends DataType implements Serializable{
	public Symbol ty;
	public NameTy(Symbol t)
	{
		ty=t;
	}
}
