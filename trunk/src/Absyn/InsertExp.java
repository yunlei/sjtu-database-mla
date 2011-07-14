package Absyn;

import Symbol.Symbol;

public class InsertExp extends Exp{
	Symbol name;
	ConstValueList constvalue;
	SelectExp select;
	NameList namelist;
	public InsertExp(int p,Symbol n,ConstValueList c)
	{
		this.pos=p;
		name=n;
		constvalue=c;
	}
	public InsertExp(int p,Symbol n,SelectExp s)
	{
		this.pos=p;
		name=n;
		select=s;
	}
	public InsertExp(int p,Symbol n,NameList namelist,ConstValueList c)
	{
		this.pos=p;
		name=n;
		this.namelist=namelist;
		constvalue=c;
	}
	public InsertExp(int p,Symbol n,NameList namelist,SelectExp s)
	{
		this.pos=p;
		name=n;
		select=s;
		this.namelist=namelist;
	}
}
