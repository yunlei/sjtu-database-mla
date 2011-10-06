package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
		//FileOutputStream fos=new FileOutputStream(DBInfo.DbMani.rootpath+"system\\tmp.o");
		  ByteArrayOutputStream bout=new ByteArrayOutputStream();
		ObjectOutputStream oos=new ObjectOutputStream(bout);
		oos.writeObject(o);
		oos.close();
		ByteArrayInputStream bis=new ByteArrayInputStream(bout.toByteArray());
		//FileInputStream fis=new FileInputStream("system\\tmp.o") ;
		ObjectInputStream ois=new ObjectInputStream(bis);
		ois.close();
		result=ois.readObject();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
}
