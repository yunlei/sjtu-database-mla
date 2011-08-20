package Absyn;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class CreateDatabaseExp extends CreateExp{

	public Symbol name;
	public CreateDatabaseExp(int p,Symbol n){
		pos=p;
		name=n;
	}

}
