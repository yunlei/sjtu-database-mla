package Running;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import semant.Env;
import semant.Semant;

import java_cup.runtime.DefaultSymbolFactory;
import java_cup.runtime.SymbolFactory;

import Absyn.Exp;
import Absyn.SQLList;
import Alge.RelaList;
import ErrorMsg.ErrorList;
import ErrorMsg.ErrorMsg;
import Parse.*;
 
public class database {
	public String username;
	public String database;
	
	public database(String username, String database) {
		this.username = username;
		this.database = database;
	}
	 
	public static  String  runing(String  inputStr,String db,String username) throws IOException
	{
		String userresult="";
		try {
			InputStream input=new ByteArrayInputStream(inputStr.getBytes());
			//FileInputStream input=new FileInputStream(getFile.getFile("G:\\slide\\db\\øŒ≥Ã…Ëº∆\\testcase", "txt"));
			Lexer lexer=new Lexer(input);
			println(inputStr);
			SymbolFactory sf=new DefaultSymbolFactory();
			AdvancedParser parser=new AdvancedParser(lexer,sf);
			//DBInfo.DbMani.addUser("admin", "admin");
			Exp result=(Exp) parser.parse().value; 
		//	print.printExp(result);
			Env env=new Env(username,db);
			Semant semant=new Semant(env);
			Execute.Execute exe=new Execute.Execute(env);
			SQLList sqlList=(SQLList)result;
			//List<ErrorList> errorlist=new ArrayList<ErrorList>();
			while(sqlList!=null){
				try{
					RelaList list=semant.transSQLs(new SQLList(sqlList.first,null));
					if(semant.hasError())
					{
						semant.printError();
						//errorlist.add(semant.getErrorlist());
						//println("execution is stoped because of the semantic error.see the log");
						
					}
					else{
						
						String strres=exe.execute(list);
						if(exe.hasError())
						{
							exe.printError();
							//errorlist.add(exe.getErrorlist());
							
						}
						else {
							//strres=strres.replaceAll(";", "\n");
							userresult+=strres;
						}
					}
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					 userresult+="ERROR "+e.getMessage()+"<SQL>";
				}
				sqlList=sqlList.next;
			}
			if(semant.hasError())
				userresult+=semant.getErrorlist().toString();
			if(exe.hasError())
				userresult+=exe.getErrorlist().toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 userresult+="ERROR "+e.getMessage()+"<SQL>";
		}
		println(userresult);
		return userresult;
		
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
