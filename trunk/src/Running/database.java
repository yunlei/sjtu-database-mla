package Running;
import java.io.ByteArrayInputStream;
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
	public String username;
	public String database;
	
	public database(String username, String database) {
		this.username = username;
		this.database = database;
	}
	 
	public static  void runing(InputStream input) throws IOException
	{
		try {
			//InputStream input=new ByteArrayInputStream(inputStr.getBytes());
			//FileInputStream input=new FileInputStream(getFile.getFile("G:\\slide\\db\\�γ����\\testcase", "txt"));
			Lexer lexer=new Lexer(input);
			SymbolFactory sf=new DefaultSymbolFactory();
			AdvancedParser parser=new AdvancedParser(lexer,sf);
			//DBInfo.DbMani.addUser("admin", "admin");
			Exp result=(Exp) parser.parse().value; 
		//	print.printExp(result);
			Env env=new Env("myl",null);
			Semant semant=new Semant(env);
			SQLList sqlList=(SQLList)result;
			while(sqlList!=null){

				RelaList list=semant.transSQLs(new SQLList(sqlList.first,null));
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
				}
				else {
					strres=strres.replaceAll(";", "\n");
					System.out.print(strres);
				}
				
				sqlList=sqlList.next;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			StackTraceElement [] ste=e.getStackTrace();
			for(int i=0;i<ste.length;i++){
				System.out.print(ste);
			}
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
