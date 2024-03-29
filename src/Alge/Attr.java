package Alge;

import java.io.Serializable;

import Absyn.BoolExp;
import Absyn.ColName;
import Absyn.ConstValue;

public class Attr implements Serializable{
	public String tableName;
	public String name;
	public Type type;
	public boolean not_null;
	public Absyn.ConstValue defaultValue;
	public boolean auto_incre;
	public boolean key;
	/**check**/
	public Absyn.BoolExp check;
	
	/***/
	public boolean fk;
	public ColName foreign_key;
	
	public Attr(String tableName, String name, Type type, boolean not_null,
			ConstValue defaultValue, boolean auto_incre, boolean key ) {
		this.tableName = tableName;
		this.name = name;
		this.type = type;
		this.not_null = not_null;
		this.defaultValue = defaultValue;
		this.auto_incre = auto_incre;
		this.key = key;
		this.fk = false;
	}
	public Attr(String name, Type type, boolean not_null,
			ConstValue defaultValue, boolean auto_incre, boolean key) {
		this.name = name;
		this.type = type;
		this.not_null = not_null;
		this.defaultValue = defaultValue;
		this.auto_incre = auto_incre;
		this.key = key;
		this.fk=false;
	}
	public Attr(BoolExp check) {
		this.check = check;
		this.tableName =null;
		this.name = null;
		this.type = null;
		this.not_null = false;
		this.defaultValue =null;
		this.auto_incre = false;
		this.key = key;
		this.fk = false;
	}
	 
	
	
}
