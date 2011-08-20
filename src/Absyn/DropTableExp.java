package Absyn;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class DropTableExp extends DropExp{
	public NameList namelist;

	public DropTableExp(int p,NameList namelist) {
		pos=p;
		this.namelist = namelist;
	}
 
 
}
