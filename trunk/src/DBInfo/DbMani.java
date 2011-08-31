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
	public static  PrioList getPrioList(){
		File file=new File(rootpath+"system" +"\\priority.list");
		if(!file.exists())
			return null;
		ObjectInputStream ois;
		try {
			 ois = new ObjectInputStream(new FileInputStream(file)); 
			 PrioList list=(PrioList) ois.readObject();
			 ois.close();
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
	public static void putPrioList(PrioList pl){
		File file=new File(rootpath+"system" +"\\priority.list");
		try {
			if(!file.exists())
				file.createNewFile();
			ObjectOutputStream ois;
		
			 ois = new ObjectOutputStream(new FileOutputStream(file)); 
			 ois.writeObject(pl);
			 ois.close();			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void addUserPrio(UserPrio p){
		PrioList list=getPrioList();
		list.add(p);
		putPrioList(list);
	}
	public static boolean hasTable(String db,String tablename){		
		File file=new File(rootpath+db+"\\"+tablename+".attr");
		return file.exists();
	}
	public static boolean hasUser(String name){
		File file = new File(rootpath + "system" + "\\user.list");

		try {
			if (!file.exists())
				return false;
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					file));
			UserList userlist = (UserList) ois.readObject();
			ois.close();
			if (userlist == null)
				return false;
			
			
			for(int i=0;i<userlist.size();i++){
				if(userlist.get(i).username.equals(name))
					return true;
			}
			return false;
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
		return false; 
	}
	public static boolean CheckPrio(String tablename ,String username,int Prio){
		PrioList pList=getPrioList();
		if(pList==null)
			return false;
		for(int i=0;i<pList.size();i++){
			UserPrio up=pList.get(i);
			if(up.tablename.equals(tablename)&&up.username.equals(username)){
				if((up.priority&Prio)!=0)
					return true;
			}
		}
		return false;
	}
	public static boolean checkUser(String name,String pw){
		File file = new File(rootpath + "system" + "\\user.list");

		try {
			if (!file.exists())
				return false;
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					file));
			UserList userlist = (UserList) ois.readObject();
			ois.close();
			if (userlist == null)
				return false; 
			for(int i=0;i<userlist.size();i++){
				if(userlist.get(i).username.equals(name)&&
						userlist.get(i).password.equals(pw))
					return true;
			}
			return false;
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
		return false; 
	}
	public static void deleteUser(String name,String pw){
		File file = new File(rootpath + "system" + "\\user.list");

		try {
			if (!file.exists())
				 return ;
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					file));
			UserList userlist = (UserList) ois.readObject();
			ois.close();
			if (userlist == null)
				return ;				
			for(int i=0;i<userlist.size();i++){
				if(userlist.get(i).username.equals(name)&&
						userlist.get(i).password.equals(pw))
					userlist.remove(i);
			}
			 ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
			  
			 oos.writeObject(userlist);
			 oos.close();
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
	}
	public static void addUser(String name,String pw){
		File file=new File(rootpath+"system"+"\\user.list");
		
		try {
			UserList userlist = null;
			if(!file.exists())
				file.createNewFile();
			
			else{ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));  
			 userlist=(UserList) ois.readObject();
			 ois.close();
			 }
			 if(userlist==null)
				 userlist=new UserList();
			 userlist.add(new UserInfo(name,pw));
			 
			 ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
			  
			 oos.writeObject(userlist);
			 oos.close();
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
	}
	public static void addIndexInfo(String database,String tablename,String col)
	{
		IndexList list=getIndexList(database);
		if(list==null)
			list=new IndexList();
		list.add(new Index(database,tablename,col));
		putIndexList(database,list);
	}
	public static void removeIndexInfo(String database,String tablename,String col){
		IndexList indexlist=getIndexList(database);
		if(indexlist==null)
			indexlist=new IndexList();
		for(int i=0;i<indexlist.size();i++){
			Index index=indexlist.get(i);
			if(index.table.equals(tablename)
					&&index.col.equalsIgnoreCase(col)){
				indexlist.remove(i); 
				i--;
			}
		}
		//list.add(new Index(database,tablename,col));
		putIndexList(database,indexlist);
	}
	public static void putIndexList(String database,IndexList il)
	{
		File file=new File(rootpath+database+"\\index.list");
		
		try {
			if(!file.exists())
				file.createNewFile();
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
			
			oos.writeObject(il);
			oos.close();
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
			 ois.close();
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
			if(size<0)
				result+="";
			else
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
	public static void deleteAFile(String subpath) throws Exception{
		File file=new File(rootpath+subpath);
		if(!file.delete())
			;//throw new Exception("delete error :"+rootpath+subpath);
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
	public static ViewList getViewList(String db){
		File file=new File(DBInfo.DbMani.rootpath+db+"\\view.list");
		ViewList viewlist=null;
		if(!file.exists())
		{	
				return null;			
		}
		else
		{
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(new FileInputStream(file));
				viewlist=(ViewList)ois.readObject();
				ois.close();
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
			
		}
		return viewlist;
	}
	public static void putViewList(String db,ViewList vl){
		File file=new File(rootpath+db+"\\index.list");
		
		try {
			if(!file.exists())
				file.createNewFile();
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(vl);
			oos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  static Alge.AttrList getAttriList(String db,String table)
	{
		try{
			String filepath=DBInfo.DbMani.rootpath+db+"\\"+table+".attr";
			File file=new File(filepath);
			if(!file.exists())
				return null;
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(file));
			Alge.AttrList list=(AttrList) ois.readObject();
			ois.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	public static SelectExp getViewDef(String db,String table)
	{
		ViewList viewlist=getViewList(db);
		while(viewlist!=null){
			if(viewlist.view.name.equalsIgnoreCase(table)){
				return viewlist.view.select;
			}
			viewlist=viewlist.next;
		}
		return null;
	}
	
	
	
}
