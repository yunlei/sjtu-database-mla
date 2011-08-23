package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class UseDatabaseExp extends Exp implements Serializable{

	public Symbol name;
	public UseDatabaseExp(int p,Symbol n){
		pos=p;
		name=n;
	}

}