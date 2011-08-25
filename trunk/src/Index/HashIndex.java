package Index;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class HashIndex
{
	public String database;
	public String table ;
	public String col;
	public String indexname;
	public IndexMap hash; 
	public HashIndex(String database, String table, String col,
			String indexname, IndexMap hash) {
		this.database = database;
		this.table = table;
		this.col = col;
		this.indexname = indexname;
		this.hash = hash;
	}
	public HashIndex(String database, String table, String col,
			String indexname ) {
		this.database = database;
		this.table = table;
		this.col = col;
		this.indexname = indexname;
		this.hash =new IndexMap();
	}
	public void putData()
	{
		FileOutputStream fin;
		try {
			fin = new FileOutputStream(DBInfo.DbMani.rootpath+this.database+
					"\\"+this.table+".index");
			ObjectOutputStream fout = new ObjectOutputStream(fin);
			fout.writeObject(hash);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public void getData(){
		FileInputStream fin;
		try {
			fin = new FileInputStream(DBInfo.DbMani.rootpath+this.database+
					"\\"+this.table+".index");
			ObjectInputStream fout = new ObjectInputStream(fin);
			this.hash=(IndexMap) fout.readObject();
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
	
	public void addPos(Object key,Object o)
	{
		List list=(List) this.hash.get(key);
		if(list==null)
			list=new ArrayList<Integer>();
		list.add(o);
		this.hash.put(key, list);
	}
	public void deletePos(Object key,Object o)
	{
		List list=(List) this.hash.get(key);
		if(list==null)
			return ;
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i).equals(o))
			{	
				list.remove(i);
				break;
			}			
		}
		this.hash.put(key, list);
	}
	public boolean containPos(Object key,Object o)
	{
		List list=(List) this.hash.get(key);
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i).equals(o))
			{	return true;
			}			
		}
		return false;
	}
	
}