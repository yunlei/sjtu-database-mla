package common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Op {
	static public Object copy(Object o) 
	{
		Object result=null;
		try{
		FileOutputStream fos=new FileOutputStream("shap.o");
		  
		ObjectOutputStream oos=new ObjectOutputStream(fos);
		oos.writeObject(o);
		FileInputStream fis=new FileInputStream("shap.o") ;
		ObjectInputStream ois=new ObjectInputStream(fis);
		result=ois.readObject();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
}
