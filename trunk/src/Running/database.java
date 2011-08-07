package Running;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java_cup.runtime.DefaultSymbolFactory;
import java_cup.runtime.SymbolFactory;

import Absyn.Exp;
import ErrorMsg.ErrorMsg;
import Parse.*;
 
public class database {
	public static  void runing() throws IOException
	{
		FileInputStream input=new FileInputStream(getFile.getFile("G:\\slide\\db\\øŒ≥Ã…Ëº∆\\testcase", "txt"));
		Lexer lexer=new Lexer(input);
		SymbolFactory sf=new DefaultSymbolFactory();
		AdvancedParser parser=new AdvancedParser(lexer,sf);
		try {
			Exp result=(Exp) parser.parse().value; 
			print.printExp(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
