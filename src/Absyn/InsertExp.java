package Absyn;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class InsertExp extends Exp{
	public Symbol name;
	public ConstValueList constvalue;
	public SelectExp select;
	public NameList namelist;
	/*insert into table values (cosnt value)*/
	public InsertExp(int p,Symbol n,ConstValueList c)
	{
		this.pos=p;
		name=n;
		constvalue=c;
		namelist=null;
	}
	/*insert into table (select )*/
	
	public InsertExp(int p,Symbol n,SelectExp s)
	{
		this.pos=p;
		name=n;
		select=s;
		namelist=null;
	}
	public InsertExp(int p,Symbol n,NameList namelist,ConstValueList c)
	{
		this.pos=p;
		name=n;
		this.namelist=namelist;
		constvalue=c;
		select =null;
	}
	public InsertExp(int p,Symbol n,NameList namelist,SelectExp s)
	{
		this.pos=p;
		name=n;
		select=s;
		this.namelist=namelist;
	}
}
