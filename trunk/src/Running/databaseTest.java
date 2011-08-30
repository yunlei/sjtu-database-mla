/**
 * 
 */
package Running;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author MaYunlei
 *
 */
public class databaseTest {

	  public static void main(String args[])
	  {
		  try {
			  File file=new File("G:\\slide\\db\\øŒ≥Ã…Ëº∆\\testcase");
			  File[] files=file.listFiles();
			  for(int i=0;i<files.length;i++){
				  print(files[i].getName()+"\n");
				  database.runing(new FileInputStream(files[i]));
			  }
			  
//		 	database.runing(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  public static void print(String s){
		  System.out.print(s);
	  }
}
