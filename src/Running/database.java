package Running;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java_cup.runtime.DefaultSymbolFactory;
import java_cup.runtime.SymbolFactory;

import ErrorMsg.ErrorMsg;
import Parse.*;
public class database {
	public static  void main(String []args) throws IOException
	{
		FileInputStream input=new FileInputStream(getFile.getFile("g:/", "txt"));
		Lexer lexer=new Lexer(input);
//		while(lexer.hasNext())
//		{
//			System.out.print(lexer.nextToken()+"\n");
//		}
		ErrorMsg errormsg=new ErrorMsg();
		SymbolFactory sf=new DefaultSymbolFactory();
		AdvancedParser parser=new AdvancedParser(lexer,sf);
		try {
			parser.parse(); 
			print.printExp(parser.getParseResult());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
