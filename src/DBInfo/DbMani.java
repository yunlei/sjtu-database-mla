package DBInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

import Absyn.SelectExp;
import Alge.AttrList;
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
		finally{
			
		}
	}
	public static void addIndexInfo(String database,String tablename,String col)
	{
		IndexList list=getIndexList(database);
		if(list==null)
			list=new IndexList();
		list.add(new Index(database,tablename,col));
		putIndexList(database,list);
	}
	public static void putIndexList(String database,IndexList il)
	{
		File file=new File(rootpath+database+"\\index.list");
		
		try {
			if(!file.exists())
				file.createNewFile();
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(il);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static IndexList getIndexList(String database )
	{
		File file=new File(rootpath+database +"\\index.list");
		if(!file.exists())
			return null;
		ObjectInputStream ois;
		try {
			 ois = new ObjectInputStream(new FileInputStream(file)); 
			 IndexList list=(IndexList) ois.readObject();
			return list;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public static long getfilesize(String database,String tablename)
	{
		File file = new File(rootpath+database+"\\"+tablename+".data");
		return file.length();
	}
	public static String read(String name, int index, int num) {
		byte[] b = null;
		File file = new File(name);
		int total=0;
		if(!file.exists())
			return null;
		try {
			RandomAccessFile rFile = new RandomAccessFile(name, "rw");
			if(num >= 0) {
				b = new byte[num];
				rFile.seek(index);
				total=rFile.read(b);
			} else {
				b = new byte[1024];
				rFile.seek(index);
				total=rFile.read(b);
			}
			rFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(b,0,total);		
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
			result+=new String(bytes,0,size);
			}while(size==1024);
			fis.close();
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
			RandomAccessFile rFile = new RandomAccessFile(file, "rw");
		//	b = content.getBytes();
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
		
		File dbfile=new File(DBInfo.DbMani.rootpath+dbname);
		return dbfile.mkdir(); 
		 
		
	}
	public  static Alge.AttrList getAttriList(String db,String table)
	{
		try{
			File file=new File(DBInfo.DbMani.rootpath+db+"\\"+table+".attr");
			if(!file.exists())
				return null;
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(file));
			Alge.AttrList list=(AttrList) ois.readObject();
			return list;
		}
		catch(Exception e)
		{
			return null;
		}
		
	}
	public static SelectExp getViewDef(String db,String table)
	{
		return null;
	}
	
	
	
}
