package Alge;

import Absyn.NameList;

public class Drop extends Relation{
	public static int DROPVIEW=1;
	public static int DROPTABLE=2;
	public static int DROPINDEX=3;
	public static int DROPDATABASE=4;
	public int choice;
	public NameList tabalename;
	public String name;
	public String indexname;
	/**
	 * 
	 * @param choice
	 * @param tabalename
	 * drop table namelist
	 */
	public Drop(int choice, NameList tabalename) {
		this.choice = choice;
		this.tabalename = tabalename;
	}
	/**
	 * 
	 * @param choice
	 * @param name
	 * drop view name
	 * drop database dbname
	 */
	public Drop(int choice, String name) {
		this.choice = choice;
		this.name = name;
	}
	/**
	 * 
	 * @param choice
	 * @param name
	 * @param indexname
	 * drop index indexname on tablenamt
	 */
	public Drop(int choice, String name, String indexname) {
		this.choice = choice;
		this.name = name;
		this.indexname = indexname;
	}
	
	
}
