package DBInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import Absyn.SelectExp;
import Symbol.Symbol;

/**
 * 
 * @author MaYunlei
 * operations of db files
 */
public class DbMani {
	static public  String rootpath="G:\\DB\\";
	public static boolean getDB(Symbol name)
	{
		try{
			File file=new File(rootpath+"\\"+name);
			return file.exists();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;	
		}
	}
	public static long getfilesize(String database,String tablename)
	{
		File file = new File(rootpath+database+"\\"+tablename+".data");
		return file.length();
	}
	public static String read(String name, int index, int num) {
		byte[] b = null;
		File file = new File(name);
		if(!file.exists())
			return null;
		try {
			RandomAccessFile rFile = new RandomAccessFile(name, "rw");
			if(num >= 0) {
				b = new byte[num];
				rFile.seek(index);
				rFile.read(b);
			} else {
				b = new byte[1024];
				rFile.seek(index);
				rFile.read(b);
			}
			rFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(b);		
	}
	public static String readFile(String database,String tablename)
	{
		
		try {
			File file=new File(rootpath+database+"\\"+tablename+".data");
			FileInputStream fis=new FileInputStream(file);
			byte[] bytes=new byte[1024];
			String result="";
			int size;
			do{
			size=fis.read(bytes);
			result+=new String(bytes);
			}while(size==1024);
			return result;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		return ""; 
	}
	public static void delteFile(String database,String tablename) {
		File file=new File(rootpath+database+"\\"+tablename+".data");
		file.delete();
	}
	public static void write(String database,String tablename,String content,long length,long l)
	{
		File file = new File(rootpath+database+"\\"+tablename+".data");
		byte[] b;
		try {
			if(!file.exists())
				file.createNewFile();
			RandomAccessFile rFile = new RandomAccessFile(tablename, "rw");
			b = content.getBytes();
			rFile.seek(length);
			rFile.writeBytes(content);
			rFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
	}
	public static void createNewTableFile(String dbname,String tablename) throws IOException
	{
		
		File datafile;
		 
		datafile=new File(DBInfo.DbMani.rootpath+dbname+"\\"+tablename+".data");
		if(!datafile.exists())
			datafile.createNewFile();
	}
	public static boolean createDB(String dbname)
	{ 
		return true;
	}
	public  static Alge.AttrList getAttriList(String db,String table)
	{
		return null;
	}
	public static SelectExp getViewDef(String db,String table)
	{
		return null;
	}
	
	
	
}
