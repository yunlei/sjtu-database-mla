package Parse;
import Absyn.*;
public class print {
	public  void printExp(Exp e)
	{
		if(e instanceof CreateExp)
			printCreate((CreateExp)e);
	}
	public void printCreate(CreateExp c)
	{
		if (c instanceof CreateTableExp)
		{
			CreateTableExp ct=(CreateTableExp)c;
			print("create table "+ct.getName()+"\n\t");
			printElementList( ct.getElement());
			print("\n");
		}
		if (c instanceof CreateIndexExp)
		{
			CreateIndexExp ct=(CreateIndexExp)c;
			print("create ");
			if(ct.isUnique())
				print(" unique ");
			print( "Index "+ct.getIndex_name()+" for table "+ct.getTable_name()+ " column name "+ct.getCol_name()+"\n");
		}
		if (c instanceof CreateDatabaseExp)
		{
			CreateDatabaseExp ct=(CreateDatabaseExp)c;
			print("create database "+ct.name+"\n");
		}
	}
	public  void printElementList(CreateElementList element) {
		// TODO Auto-generated method stub
		if(element.next==null)
			return;
		 printElement(element.first);
		 printElementList(element.next);
	}
	private void printElement(CreateElement c) {
		// TODO Auto-generated method stub
		if(c instanceof ColumnDefinition)
		{
			ColumnDefinition cd=(ColumnDefinition)c;
		}
	}
	public void print(String s)
	{
		System.out.print(s);
	}
}
