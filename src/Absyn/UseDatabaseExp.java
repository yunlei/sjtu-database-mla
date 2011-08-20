package Absyn;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class UseDatabaseExp extends Exp{

	public Symbol name;
	public UseDatabaseExp(int p,Symbol n){
		pos=p;
		name=n;
	}

}