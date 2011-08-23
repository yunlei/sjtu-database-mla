package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class CreateDatabaseExp extends CreateExp implements Serializable{

	public Symbol name;
	public CreateDatabaseExp(int p,Symbol n){
		pos=p;
		name=n;
	}

}
