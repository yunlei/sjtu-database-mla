package Absyn;

import Symbol.Symbol;

public class CreateDatabaseExp extends Exp{

	public Symbol name;
	public CreateDatabaseExp(int p,Symbol n){
		pos=p;
		name=n;
	}

}
