package Running;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import Parse.*;
public class database {
	public static  void main(String []args) throws IOException
	{
		FileInputStream input=new FileInputStream(getFile.getFile("g:/", "txt"));
		Lexer lexer=new Lexer(input);
		AdvancedParser parser=new AdvancedParser(lexer);
		
	}
}
