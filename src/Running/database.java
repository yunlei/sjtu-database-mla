package Running;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import semant.Env;
import semant.Semant;

import java_cup.runtime.DefaultSymbolFactory;
import java_cup.runtime.SymbolFactory;

import Absyn.Exp;
import Absyn.SQLList;
import Alge.RelaList;
import ErrorMsg.ErrorMsg;
import Parse.*;
 
public class database {
	public static  void runing() throws IOException
	{
		try {
			FileInputStream input=new FileInputStream(getFile.getFile("G:\\slide\\db\\øŒ≥Ã…Ëº∆\\testcase", "txt"));
			Lexer lexer=new Lexer(input);
			SymbolFactory sf=new DefaultSymbolFactory();
			AdvancedParser parser=new AdvancedParser(lexer,sf);
		
			Exp result=(Exp) parser.parse().value; 
			print.printExp(result);
			Env env=new Env("myl",null);
			Semant semant=new Semant(env);
			RelaList list=semant.transSQLs((SQLList)result);
			if(semant.hasError())
			{
				semant.printError();
				println("execution is stoped because of the semantic error.see the log");
				return ;
			}
			Execute.Execute exe=new Execute.Execute(env);
			String strres=exe.execute(list);
			if(exe.hasError())
			{
				exe.printError();
				return ;
			}
			strres=strres.replaceAll(";", "\n");
			System.out.print(strres);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void println(String str)
	{
		System.out.println(str);
	}
	public void print(String str)
	{
		System.out.print(str);
	}
}
