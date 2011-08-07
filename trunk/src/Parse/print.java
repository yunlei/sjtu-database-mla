package Parse;
import Symbol.Symbol;
import Absyn.*;
public class print {
	public  static void printExp(Exp e)
	{
		if(e instanceof SQLList)
			printSQLList((SQLList)e);
		if(e instanceof CreateExp)
			printCreate((CreateExp)e);
		if(e instanceof SelectExp)
			printSelectExp((SelectExp)e);
		if(e instanceof AlterExp)
			printAlterExp((AlterExp)e);
		if(e instanceof BoolExp)
			printBoolExp((BoolExp)e);
		if(e instanceof DeleteExp)
			printDeleteExp((DeleteExp)e);
		if(e instanceof DropExp)
			printDropExp((DropExp)e);
		if(e instanceof InsertExp)
			printInsertExp((InsertExp)e);
		if(e instanceof UpdateExp)
			printUpdateExp((UpdateExp)e);
	}
	private static void printSQLList(SQLList e) {
		// TODO Auto-generated method stub
		if(e ==null )
			return ;
		print("{\n");
		printExp(e.first);
		print("\n} \n");
		printExp(e.next); 
	}
	private static void printUpdateExp(UpdateExp e) {
		// TODO Auto-generated method stub
		print("update "+e.name+" set ");
		printAssignList(e.assign);
		print(" where ");
		printBoolExp(e.bool);
		print("\n");
	}
	private static void printAssignList(AssignList al) {
		// TODO Auto-generated method stub
		if(al==null)
			return;
		Assignment a=al.first;
		print("("+a.var+"=");
		printValue(a.value);
		printAssignList(al.next);
	}
	private static void printInsertExp(InsertExp e) {
		// TODO Auto-generated method stub
		print("insert into "+e.name);
		if(e.namelist!=null)
			printNameList(e.namelist); 
		print(" values ");
		if(e.constvalue!=null)
			printConstValueList(e.constvalue); 
		else if(e.select!=null)
			printSelectExp(e.select); 
	} 
	private static void printConstValueList(ConstValueList cv) {
		// TODO Auto-generated method stub
		if(cv==null)
			return;
		printConstValue(cv.value);
		printConstValueList(cv.next);
	}
	private static void printNameList(NameList nl) {
		// TODO Auto-generated method stub
		if(nl==null)
			return;
		print(nl.name+" ");
		printNameList(nl.next);
	}
	private static void printDropExp(DropExp e) {
		// TODO Auto-generated method stub
		print("drop ");
		if(e instanceof DropDatabaseExp)
		{
			DropDatabaseExp d=(DropDatabaseExp)e;
			print(" database "+d.name);
		}
		if(e instanceof DropIndexExp)
		{
			DropIndexExp d=(DropIndexExp)e;
			print(" index "+d.indexname+" for "+d.tablename);
		}
		if( e instanceof DropTableExp)
		{
			DropTableExp d=(DropTableExp)e;
			print(" table "+d.name);
		}
		if(e instanceof DropViewExp)
		{
			DropViewExp d=(DropViewExp)e;
			print(" view "+d.name);
		}
	}
	private static void printDeleteExp(DeleteExp e) {
		// TODO Auto-generated method stub
		print("delete "+e.name  );
		if(e.exp!=null){
			print(" where ");
			printBoolExp(e.exp);
		}
	}
	private static void printAlterExp(AlterExp e) {
		// TODO Auto-generated method stub
		if( e instanceof AlterAddExp){
			AlterAddExp add=(AlterAddExp)e;
			print(" alter table "+add.tablename+" add ");
			print(add.col+" ");
			printDataType(add.type);
		}
		if( e instanceof AlterDropExp){
			AlterDropExp add=(AlterDropExp)e;
			print("alter table "+add.tablename);
			print(" drop "+add.col);
		}
		
	}
	public  static void printSelectExp(SelectExp e) {
		// TODO Auto-generated method stub
		 print("select "+e.distinct_or_not +" ");
		 printSelectExpr(e.selectexpr);
		  
		 printFrom(e.fromclause);
		 if(e.whereclause!=null){
			 printWhereExp(e.whereclause);
		 }
	} 
	private static void printWhereExp(WhereClause w) {
		// TODO Auto-generated method stub
		 print("where ");
		 printBoolExp(w.boolexp);
	}
	private static void printFrom(FromClause f) {
		// TODO Auto-generated method stub
		print(" from ");
		printTableRefList(f.tablereflist); 
	}
	private static void printTableRefList(TableRefList t) {
		// TODO Auto-generated method stub
		TableRef tr=t.tableref;
		if(tr.asname==null &&tr.subquery==null)
			print(tr.name+"");
		else if(tr.subquery==null)
		{
			print(tr.name+" as " + tr.asname);
		}
		else {
			print("(");
			printSelectExp(tr.subquery);
			print(") as ");
			print(""+tr.asname);
		}
	}
	private static void printSelectExpr(SelectExpr s) {
		// TODO Auto-generated method stub
		if(s==null)
			return;
		printValue(s.value);
		if(s.alias!=null){
			print(" as ");
			print(s.alias.name+"");
		}
		printSelectExpr(s.next);
		
	}
	public static void printCreate(CreateExp c)
	{
		if (c instanceof CreateTableExp)
		{
			CreateTableExp ct=(CreateTableExp)c;
			print("create table "+ct.getName()+"\n\t");
			print("(");
			printElementList( ct.getElement());
			print(")");
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
	public  static void printElementList(CreateElementList element) {
		// TODO Auto-generated method stub
		if(element==null)
			return;
		 printElement(element.first);
		 if(element.next!=null)print(",");
		 printElementList(element.next);
		 
	}
	private static void printElement(CreateElement c) {
		// TODO Auto-generated method stub
		if(c instanceof ColumnDefinition)
		{
			ColumnDefinition cd=(ColumnDefinition)c;
			print("<"+cd.name+" ");
			printDataType(cd.type);
			print(" "+cd.key_or_not+" "+cd.null_or_not+" "+cd.auto_increment_or_not+" ");
			if(cd.defaultvalue!=null)
			{
				print(" default value:");
				printConstValue(cd.defaultvalue);
			}
			if(cd.check_exp!=null)
			{
				print(" check:");
				printBoolExp(cd.check_exp);
			}
		}
	}
	private static void printDataType(DataType t) {
		// TODO Auto-generated method stub
		 if(t instanceof NameTy){
			 print(""+((NameTy)t).ty);
		 }
		 if(t instanceof ArrayTy){
			 ArrayTy at=(ArrayTy)t;
			 print(at.ty+"("+at.length+")");
		 }
	}
	private static void printBoolExp(BoolExp e) {
		// TODO Auto-generated method stub
		if(e ==null )
			return;
		if(e instanceof AllExp)
		{
			AllExp ae=(AllExp)e;
			printValue(ae.value);
			print(" all ");
			printCmp(ae.comp);
			print(" (");
			printExp(ae.select);
			print(")");
		}
		if( e instanceof AnyExp)
		{
			AnyExp aa=(AnyExp)e;
			printValue(aa.value);
			print(" all ");
			printCmp(aa.comp);
			print(" (");
			printExp(aa.select);
			print(")");
		}
		if( e instanceof BoolAndExp)
		{
			BoolAndExp b=(BoolAndExp)e;
			print("(");
			printBoolExp(b.exp1);
			print(" and ");
			printBoolExp(b.exp2);
			print(")");
		}
		if( e instanceof BoolExsitExp)
		{
			BoolExsitExp b=(BoolExsitExp)e;
			print("exists (");
			printSelectExp(b.select);
			print(")");
		}
		if(e instanceof BoolOrExp)
		{
			BoolOrExp b=(BoolOrExp)e;
			print("(");
			printBoolExp(b.exp1);
			print(" or ");
			printBoolExp(b.exp2);
			print(")");
		}
		if( e instanceof InExp)
		{
			InExp i=(InExp)e;			
			printValue(i.value);
			print(" in ");
			print(" (");
			printExp(i.select);
			print(")");
		}
		if(e instanceof LikeEscapeExp)
		{
			LikeEscapeExp l=(LikeEscapeExp)e;
			printValue(l.value);
			if(l.like_or_not)
				print(" like ");
			else
				print(" not like ");
			print(l.likestring);
			if(l.escape_or_not)
			{
				print(" escape "+l.escapestring);
			}
		}
		if(e instanceof CompBoolExp)
		{
			CompBoolExp cbe=(CompBoolExp)e;
			printValue(cbe.v1);
			printCmp(cbe.comp );
			printValue(cbe.v2);
		}
	}
	private static void printCmp(Symbol comp) {
		// TODO Auto-generated method stub
		print(" ");
		 if(comp.toString().equals("LT"))
			 print("<");
		 if(comp.toString().equals("LE"))
			 print("<=");
		 if(comp.toString().equals("GT"))
			 print(">");
		 if(comp.toString().equals("EQ"))
			 print("=");
		 if(comp.toString().equals("GE"))
			 print(">=");
		 if(comp.toString().equals("NEQ"))
			 print("<>");
		 print(" ");
	}
	private static void printValue(Value v) {
		// TODO Auto-generated method stub
		if(v instanceof ColValue)
		{
			printColName(((ColValue)v).name);
		} 
		if(v instanceof ConstValue)
		{
			ConstValue cv=(ConstValue)v;
			printConstValue(cv);
		}
		if( v instanceof FuncValue)
		{
			printFuncValue((FuncValue)v);
		}
		if(v instanceof OperValue)
		{
			printOperValue((OperValue)v);
		}
		if(v instanceof SubqueryValue)
		{
			printSelectExp((SelectExp)(((SubqueryValue)v).select));
		}
	} 
	private static void printOperValue(OperValue v) {
		// TODO Auto-generated method stub
		print("(");
		printValue(v.v1);
		print(v.op+"");
		printValue(v.v2);
	} 
	private static void printFuncValue(FuncValue v) {
		// TODO Auto-generated method stub
		print(v.functy+"(");
		printColName(v.colname);
		print(")");		
	}
	private static void printColName(ColName name) {
		// TODO Auto-generated method stub
		if(name.table!=null)
			print(name.table+".");
		print(name.col+"");
	}
	private static void printConstValue(ConstValue cv) {
		// TODO Auto-generated method stub
		 if(cv instanceof ConstValueBoolean)
		 {
			 ConstValueBoolean cvb=(ConstValueBoolean)cv;
			 if(cvb.flag){
				 print("true");
			 }
			 else
				 print("false");			             
		}
		if (cv instanceof ConstValueFloat) 
		{
			ConstValueFloat cvf = (ConstValueFloat) cv;			
			print("" + cvf);
		}
		if(cv instanceof ConstValueInt){
			print((ConstValueInt)cv+"");
		}
		if(cv instanceof ConstValueString){
			print(((ConstValueString)cv).value+"");
		}
		if(cv instanceof ConstValueNull){
			print("NULL");
		}
		

		 
	}
	public static void print(String s)
	{
		System.out.print(s);
	}
}
