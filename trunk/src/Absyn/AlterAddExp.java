package Absyn;

import java.io.Serializable;

import Symbol.Symbol;

/**
 * @author MaYunlei
 *
 */
public class AlterAddExp extends AlterExp implements Serializable{
	public Symbol tablename;
	public Symbol col;
	public DataType type;
	public AlterAddExp(int p,Symbol tn,Symbol c,DataType t)
	{
		this.pos=p;
		col=c;
		tablename=tn;col=c;
		type=t;
	}
}
