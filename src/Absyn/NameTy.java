package Absyn;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class NameTy extends DataType{
	public Symbol ty;
	public NameTy(Symbol t)
	{
		ty=t;
	}
}
