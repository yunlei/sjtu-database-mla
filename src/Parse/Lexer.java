package Parse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;


public class Lexer implements java_cup.runtime.Scanner{
	Yylex yylex;
	ArrayList<java_cup.runtime.Symbol> list=new ArrayList<java_cup.runtime.Symbol>();
	private boolean haserror;
	
	private Iterator it;
	public Lexer(InputStream input) throws IOException{
		it=list.iterator();
		yylex=new Yylex(input);
		this.haserror=false;
		while(true){
			java_cup.runtime.Symbol symbol=yylex.nextToken();
			if(symbol.sym==sym.ERROR)
			{
				report_error(symbol);
			}
			else if(symbol.sym==sym.EOF)
			{
				list.add(symbol);
				break;
			}
			else 
				list.add(symbol);
		}
	}
	public java_cup.runtime.Symbol nextToken()
	{
		return (java_cup.runtime.Symbol)it.next();
	}
	public boolean hasNext()
	{
		if(it.hasNext()&&((java_cup.runtime.Symbol)it.next()).sym!=sym.EOF)
			return true;
		return false;
	}
	public void report_error(java_cup.runtime.Symbol symbol  )
	{
		this.haserror=true;
		System.out.print("lexical error @"+symbol.left+":"+symbol.value);
	}
	public boolean hasError()
	{
		return this.haserror;
	}
}