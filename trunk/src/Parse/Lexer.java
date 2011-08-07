package Parse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;


public class Lexer implements java_cup.runtime.Scanner{
	Yylex yylex;
	ArrayList<java_cup.runtime.Symbol> list=new ArrayList<java_cup.runtime.Symbol>();
	private boolean haserror;
	
	private int ptr;
	public Lexer(InputStream input) throws IOException{
		 
		yylex=new Yylex(input);
		this.haserror=false;
		while(true){
			java_cup.runtime.Symbol symbol=yylex.nextToken();
			System.out.print(symbol+"\n");
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
		if(hasNext())
			return list.get(ptr++); 
		else 
			return new java_cup.runtime.Symbol(sym.EOF);
	}
	public boolean hasNext()
	{
		if(ptr<list.size()&&list.get(ptr).sym!=sym.EOF)
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