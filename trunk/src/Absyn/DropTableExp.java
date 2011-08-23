package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class DropTableExp extends DropExp implements Serializable{
	public NameList namelist;

	public DropTableExp(int p,NameList namelist) {
		pos=p;
		this.namelist = namelist;
	}
 
 
}
