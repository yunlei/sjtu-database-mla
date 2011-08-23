package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class AsOrNot implements Serializable{
	public Symbol name;
	public AsOrNot(Symbol n){
		name=n;
	}
}
