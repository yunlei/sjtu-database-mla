package semant;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import Absyn.*;
import Alge.*;
 
import DBInfo.Index;
import DBInfo.IndexList;
import DBInfo.UserPrio;
import DBInfo.View;
import DBInfo.ViewList;
import ErrorMsg.ErrorList;
import ErrorMsg.ErrorMsg;
import Symbol.Symbol;

public class Semant{
	Env env;
	ErrorList error_list=null;
	public Semant(Env env) {
		this.env = env;
	}
	public boolean hasError()
	{
		return error_list!=null;
	}

	public ErrorList getErrorlist() {
		return error_list;
	}
	public void printError()
	{
		ErrorList tmp=error_list;
		if(tmp==null)
			return ;
		System.out.print("***************SEMANT**ERROR**********************\n");
		while(tmp!=null)
		{
			System.out.print(tmp.msg);
			System.out.print("\n");
			tmp=tmp.next;
		}
		System.out.print("*********************ERROR*END******************\n");
	}
	public void putError(String msg,int loc)
	{
		error_list=new ErrorList(new ErrorMsg( loc,msg),error_list);
	}
	public Relation transExp(Exp e) throws Exception
	{
		 
		 Relation  result = null;
		if(e instanceof CreateExp)
		{
			result= transExp((CreateExp)e);
		}
		if(e instanceof SelectExp )
		{
			result= transExp((SelectExp)e);
		}
		if(e instanceof InsertExp )
		{
			result= transExp((InsertExp)e);
		}
		if(e instanceof DeleteExp )
		{
			result= transExp((DeleteExp)e);
		}
		if(e instanceof UpdateExp )
		{
			result= transExp((UpdateExp)e);
		}
		if(e instanceof DescribeExp)
		{
			result= transExp((DescribeExp)e);
		}
		if(e instanceof DropExp )
		{
			result= transExp((DropExp)e);
		}
		if(e instanceof GrantExp )
		{
			result= transExp((GrantExp)e);
		}
		if(e instanceof UseDatabaseExp )
		{
			result= transExp((UseDatabaseExp)e);
		}
		if(e instanceof AlterExp )
		{
			result= transExp((AlterExp)e);
		}
		
		return result; 
	}
	public Relation transExp(DropExp e)
	{
		if(!checkDB(e)) 
			return null;
		if(e instanceof DropTableExp)
		{
			DropTableExp de=(DropTableExp)e;
			NameList namelist=de.namelist;
			while(namelist!=null)
			{
				String name=namelist.name.toString();
				AttrList attrlist=DBInfo.DbMani.getAttriList(env.database, name);
				if(attrlist==null)
				{
					putError("table name:"+name+" not found.",namelist.pos);
					return null;
				}
				namelist=namelist.next;
			}
			return new Drop(Drop.DROPTABLE,de.namelist);
		}
		if(e instanceof DropViewExp)
		{
			DropViewExp dve=(DropViewExp)e;
			SelectExp select=DBInfo.DbMani.getViewDef(env.database,dve.name.toString());
			if(select==null)
			{
				putError("view name:"+dve.name.toString()+" not found",e.pos);
				return null;
			}
			return new Drop(Drop.DROPVIEW,dve.name.toString());
		}
		if(e instanceof DropDatabaseExp)
		{
			DropDatabaseExp dde=(DropDatabaseExp)e;
			if(!DBInfo.DbMani.getDB(dde.name))
			{
				putError("database:"+dde.name.toString()+" not found.",e.pos);
				return null;
			}
			return new Drop(Drop.DROPDATABASE,dde.name.toString());
		}
		if(e instanceof DropIndexExp)
		{
			DropIndexExp die=(DropIndexExp)e;
			//
			File file=new File(DBInfo.DbMani.rootpath+env.database+"\\"+die.tablename.toString()+".index");
			if(!file.exists()){
				putError("index for table:"+die.tablename+" not found.",die.pos);
				return null;
			}
			IndexList indexlist=DBInfo.DbMani.getIndexList(env.database);
			boolean flag=false;
			for(int i=0;i<indexlist.size();i++){
				if(indexlist.get(i).table.equals(die.tablename.toString())
						&&indexlist.get(i).col.equalsIgnoreCase(die.indexname.toString()))
				{
					flag=true;
					break;
				}
			}
			if(!flag)
			{
				putError("index for table:"+die.tablename+" with name:"+die.indexname+" not found.",die.pos);
				return null;
			}
			return new Drop(Drop.DROPINDEX,die.tablename.toString(),die.indexname.toString());
			//putError("drop index not implement.",e.pos);
		}
		return null;		
	}
	/**
	 * ALTER  TABLE NAME  ADD NAME  data_type:dt	 
		{:RESULT=new AlterAddExp(Aleft,new Symbol(N),new Symbol(N1),dt);:}
		|ALTER  TABLE NAME  DROP NAME:N1 
		{:RESULT=new AlterDropExp(Aleft,new Symbol(N),new Symbol(N1));:}
		;
	 * @param e
	 * @return
	 */
	public Relation transExp(AlterExp e)
	{
		if(!checkDB(e)) 
			return null;
		if(e instanceof AlterAddExp)//ALTER  TABLE NAME  ADD NAME  data_type:dt
		{
			AlterAddExp aae=(AlterAddExp)e;
			AttrList attrlist=DBInfo.DbMani.getAttriList(env.database,aae.tablename.toString());
			if(attrlist==null)
			{
				putError("table:"+aae.tablename.toString()+"not found(int database"+env.database+")",e.pos);
				return null;
			}
			Type type=null;
			if(aae.type instanceof NameTy)
			{
				NameTy ty=(NameTy)aae.type;
				if(ty.ty.toString().equals("int"))
					type=new Type(Type.INT);
				if(ty.ty.toString().equals("boolean"))
					type=new Type(Type.BOOL);
			}
			if(aae.type instanceof ArrayTy)
			{
				ArrayTy at=(ArrayTy)aae.type;
				if(at.ty.toString().equals("char"))
				{
					type=new Type(Type.CHAR,at.length);
				}
			}
			return new Alter(Alter.ADD,aae.tablename.toString(),aae.col.toString(),type);
		}
		if(e instanceof AlterDropExp)
		{
			AlterDropExp ade=(AlterDropExp)e;
			AttrList attrlist=DBInfo.DbMani.getAttriList(env.database,ade.tablename.toString());
			if(attrlist==null)
			{
				putError("table:"+ade.tablename.toString()+"not found(int database"+env.database+")",e.pos);
				return null;
			}
			while(attrlist!=null)
			{
				Attr attr=attrlist.attr;
				if(attr.name.equals(ade.col.toString()))
				{
					return new Alter(Alter.DROP,ade.tablename.toString(),ade.col.toString());
				}
				attrlist=attrlist.next;
			}
			putError("colname:"+ade.col.toString()+" not found.",e.pos);
			return null;
		}
		return null;		
	}
	public Relation transExp(GrantExp e)
	{
		//putError("grant exp not implement.",e.pos);
		//grant select insert update on tablename(s) to user(s);
		NameList tables=e.table_list;
		while(tables!=null){
			if(!DBInfo.DbMani.hasTable(env.database, tables.name.toString())){
				putError("table:"+tables.name+ " doesnot exists(int grant exp).",e.pos);
				return null;
			}
			tables=tables.next;
		}
		NameList userlist=e.user_list;
		while(userlist!=null){
			if(!DBInfo.DbMani.hasUser(userlist.name.toString())){
				putError("user :"+userlist.name.toString()+" doesnot exists.",e.pos);
				return null;
			}
			userlist=userlist.next;
		}
		int priority=0;
		Privileges Pris=e.p;
		while(Pris!=null){
			Privilege p=Pris.Privilege;
			if(p.privilege.toString().equalsIgnoreCase("SELECT"))
				priority|=DBInfo.UserPrio.SELECT;
			if(p.privilege.toString().equalsIgnoreCase("INSERT"))
				priority|=DBInfo.UserPrio.INSERT;
			if(p.privilege.toString().equalsIgnoreCase("UPDATE"))
				priority|=DBInfo.UserPrio.UPDATE;
			Pris=Pris.next;
		}
		if(e.WithOption)
			priority|=DBInfo.UserPrio.GRANT;
		return new Grant(priority,e.table_list,e.user_list );
	}
	public Relation transExp(DescribeExp e)
	{
		if(!checkDB(e)) 
			return null;
		NameList namelist=e.names;
		while(namelist!=null)
		{
			String table=namelist.name.toString();
			AttrList attrlist=DBInfo.DbMani.getAttriList(env.database, table);
			if(attrlist==null)
			{
				putError("table name:"+table+"not found(in describe)",e.pos);
				return null;
			}
			namelist=namelist.next;
		}
		return new Describe(e.names);
				
	}
	public Relation transExp(UpdateExp e)
	{
		if(!checkDB(e)) 
			return null;
		//update tablename set asignment where boolexp
		//check tablename
		AttrList attrlist =DBInfo.DbMani.getAttriList(env.database, e.name.toString());
		if(attrlist==null)
		{
			putError("table:"+e.name.toString()+" not found.",e.pos);
			return null;
		}
		if(!DBInfo.DbMani.CheckPrio(e.name.toString(),env.user, UserPrio.UPDATE))
		{
			putError("the user has no priority to delete table:"+e.name,e.pos);
			return null;
		}
		AssignList assignlist=e.assign;
		if(assignlist==null)
		{
			putError("update error, assign list not found.",e.pos);
			return null;
		}
		while(assignlist!=null)
		{
			Assignment assign=assignlist.first;
			String name=assign.var.toString();
			Value v=assign.value;
			if(v instanceof ConstValue){}
			else{
				putError("right side of assign operator must be a const value.",assign.pos);
				return null;
			}			
			AttrList tmplist=attrlist;
			boolean flag=false;
			while(tmplist!=null)
			{
				Attr attr=tmplist.attr;
				if(attr.name!=null&&attr.name.equals(name)){
					flag=true;
					//check type;
					if(!typeCheck(attr.type,(ConstValue)v))
					{
						error_list.msg.setLocation(e.pos);
						return null;
					}
					break;
				}
				tmplist=tmplist.next;
			}
			if(!flag)
			{
				putError("assign parameter:"+name+" not found.",assign.pos);
				return null;
			}
			assignlist=assignlist.next;
		}
		if(e.bool!=null){
			ColNameList colnamelist=this.getColNameList(e.bool);			
			while(colnamelist!=null)
			{
				boolean flag=false;
				ColName colname=colnamelist.name;
				AttrList tmplist=attrlist;
				while(tmplist!=null)
				{
					if(tmplist.attr.name.equals(colname.col.toString()))
					{
						flag=true;
						break;
					}
					tmplist=tmplist.next;
				}
				if(!flag)
				{
					putError("colname:"+colname.col+" not found(in where clause",e.pos);
					return null;
				}
				colnamelist=colnamelist.next;
			}
		}
		 return new Update((e.name.toString()),e.assign,e.bool);	
	}
	public Relation transExp(DeleteExp e)
	{
		if(!checkDB(e)) 
			return null;
		if(!DBInfo.DbMani.CheckPrio(e.name.toString(),env.user, UserPrio.GRANT|UserPrio.INSERT|UserPrio.SELECT|UserPrio.UPDATE))
		{
			putError("the user has no priority to delete table:"+e.name,e.pos);
			return null;
		}
		String tablename=e.name.toString();
		if(e.exp!=null)
		{
			ColNameList namelist=getColNameList(e.exp);
			AttrList attrlist=DBInfo.DbMani.getAttriList(env.database,tablename);
			while(namelist!=null)
			{
				ColName colname=namelist.name;
				AttrList tmpAttrlist=attrlist;
				boolean flag=false;
				while(tmpAttrlist!=null)
				{
					Attr attr=tmpAttrlist.attr;
					if(attr.name!=null&&attr.name.equals(colname.col.toString()))
					{
						flag=true;
						break;
					}	
					tmpAttrlist=tmpAttrlist.next;
				}
				if(!flag)
				{
					putError("colname :"+colname.col+"(in where clause) not found",e.pos);
					return null;
				} 
				namelist=namelist.next;
			} 
		}
		return new Delete(tablename,e.exp);
		
	}
	public Relation transExp(UseDatabaseExp e)
	{
		if(!DBInfo.DbMani.getDB(e.name))
		{
			putError("db name"+e.name+" not exists",e.pos);
			return null;
		}
		env.database=e.name.toString();
		return new UseDB(e.name.toString());
	}
	public Relation transExp(SelectExp s) throws Exception
	{ 
		if(!checkDB(s)) 
			return null;
		
		Relation table=transFrom(s.fromclause.tablereflist);
		if(s.havingclause!=null&&s.groupclause==null)
		{
			putError("having clause must be appended after group cluase",s.pos);
			return null; 
		} 
		//check where select attr is in the table
		int selectNum=0;
		SelectExpr select_expr=s.selectexpr;
		while(select_expr!=null)
		{
			selectNum++;
			if(select_expr.value instanceof ColValue)
			{
				ColName colname=((ColValue)select_expr.value).name;
				if(colname.col.toString().equals("*"))
				{
					//replace * with real col name;
					
				}
				else
				{
					 if(!this.colNameInCrossUnion(colname, (CrossJoin)table,s.pos))
						 return null;
				}
			} 
			select_expr=select_expr.next;
		}
		//check where
		if(s.whereclause!=null){
			ColNameList colnamelist=getColNameList(s.whereclause.boolexp);
			while(colnamelist!=null)
			{
				ColName colname=colnamelist.name;
				CrossJoin u=(CrossJoin)table;
				if(!colNameInCrossUnion(colname,u,s.pos))
					return null;
				colnamelist=colnamelist.next;
			}
		}
		//check order list;
		if(s.orderclause!=null){
			OrderList orderlist=s.orderclause.orderlist;
			while(orderlist!=null)
			{
				ColName colname=orderlist.col;
				CrossJoin u=(CrossJoin)table;
				if(!colNameInCrossUnion(colname,u,s.pos))
					return null;			
				orderlist=orderlist.next;
			}
		}
		//check group list;
		
		if(s.groupclause!=null&&!colNameInCrossUnion(s.groupclause.name,(CrossJoin)table,s.pos))
			return null;
		//check having list;
		if (s.havingclause != null) {
			ColNameList colnamelist = getColNameList(s.havingclause.boolexp);
			while (colnamelist != null) {
				ColName colname = colnamelist.name;
				CrossJoin u = (CrossJoin) table;
				if (!colNameInCrossUnion(colname, u,s.pos))
					return null;
				colnamelist = colnamelist.next;
			}
		}
		//genegate the relation;
		Relation result=null;
		if(s.whereclause!=null){
			result=new Sigma(new Condition(s.whereclause.boolexp),table);
		}
		else
			result=new Sigma(null,table);
		
		
		if(s.groupclause==null)
			result=new Project(s.selectexpr,result,null,null);
		else if(s.havingclause==null)
			result=new Project(s.selectexpr,result,s.groupclause.name,null);
		else
			result=new Project(s.selectexpr,result,s.groupclause.name,new Condition(s.havingclause.boolexp));
		if(this.hasError())
			return null;
		Execute.Execute exe=(new Execute.Execute(env));
		Relation tmpr=exe.execute(result);
		if(tmpr!=null)
			result.attrlist=(AttrList) common.Op.copy(tmpr.attrlist);
		if(s.orderclause!=null)
			result=new Order(result,s.orderclause.orderlist);
		if(s.distinct_or_not!=null)
		{
			result=new Gamma(result);
			result.attrlist=(AttrList) common.Op.copy(((Gamma)result).sub.attrlist);
		}
		
		
		return result;
	}
	public boolean checkDB(Exp e)
	{
		if(env.database==null)
		{
			putError("database not selected yet.",e.pos);
			return false;
		}
		if(!DBInfo.DbMani.getDB(new Symbol(env.database)))
		{
			putError("database(with name:"+env.database+") not found.",e.pos);
		}
		return true;
	}
	public boolean colNameInCrossUnion(ColName colname,CrossJoin u1,int pos)
	{
		boolean flag=false;
		CrossJoin u=u1;
		while(u!=null&&!flag)
		{ 
			{ 
				AttrList attlist=u.leftR.attrlist;							
				while(attlist!=null)
				{
					if((colname.table==null||colname.table.equals("")||
							colname.table.toString().equals(attlist.attr.tableName))
							&&attlist.attr.check==null&&attlist.attr.name.equals(colname.col.toString()))
					{
						flag=true;
						break;
					}
					attlist=attlist.next; 
				}
			}
			u=u.next;
		}
		if(!flag)
		{
			putError("colname not found:"+colname.table+"."+colname.col,pos);
		}			
		return flag;
	}
	private CrossJoin transFrom(TableRefList tl) throws Exception {
		// TODO Auto-generated method stub 
		if(tl==null)
			return null;
		CrossJoin u;
		Relation r1 = null;
		if(tl.tableref.name!=null&&tl.tableref.subquery!=null)
		{
			putError("from table must be a table or a subquery",-1);
			return null;
		}
		if(tl.tableref.name!=null)
		{
			AttrList attrlist=DBInfo.DbMani.getAttriList(env.database, tl.tableref.name.toString());
			if(attrlist!=null){
				r1=new RealRelation(tl.tableref.name.toString()); 
				AttrList orderlist=null,tmp=attrlist;
				while(tmp!=null){
					orderlist=new AttrList(tmp.attr,orderlist);
					tmp=tmp.next;
				}
				if(!DBInfo.DbMani.CheckPrio(tl.tableref.name.toString(),env.user, UserPrio.SELECT))
				{
					throw new Exception("the user has no priority to insert table:"+tl.tableref.name);
					 
				}
				r1.attrlist=DBInfo.DbMani.getAttriList(env.database, tl.tableref.name.toString());
				r1.results=DBInfo.DbMani.readFile(env.database, tl.tableref.name.toString());
			}
			else{//is a view
				try{
					File file=new File(DBInfo.DbMani.rootpath+env.database+"\\view.list");
					ViewList viewlist=null;
					if(!file.exists())
					{	
							throw new Exception("name:"+tl.tableref.name.toString()+" not found(neither a table or a view).");			
					}
					else
					{
						ObjectInputStream ois=new ObjectInputStream(new FileInputStream(file));
						viewlist=(ViewList)ois.readObject(); 
						while(viewlist!=null)
						{
							View view=viewlist.view;
							if(view.name.equals(tl.tableref.name.toString()))
							{
								r1=transExp(view.select);
								break;
							}
							viewlist=viewlist.next;
						}
						if(viewlist==null)
							throw new Exception("name:"+tl.tableref.name.toString()+" not found(neither a table or a view).");		
					}
				}
				catch(Exception e)
				{
					putError(e.getMessage(),-1);
					return null;
				}
			}
		}
		if(tl.tableref.subquery!=null)
		{
			r1=transExp(tl.tableref.subquery);
			AttrList attrlist=r1.attrlist;
//			while(attrlist!=null)
//			{
//				attrlist.attr.tableName=tl.tableref.asname.toString();
//			}
		}
		if(tl.tableref.asname!=null)
		{
			//r1.asname=tl.tableref.asname.toString();
			//change the table name of attr of r1 to the asname;
			AttrList tmplist=r1.attrlist;
			while(tmplist!=null){
				tmplist.attr.tableName=tl.tableref.asname.toString();
				tmplist=tmplist.next;
			}
		} 
		CrossJoin next=transFrom(tl.next);
		u=new CrossJoin(r1,next);
		AttrList llist = null,rlist = null;
		if(u.leftR!=null&&u.leftR.attrlist!=null)
			llist=(AttrList) common.Op.copy(u.leftR.attrlist);
		if(u.next!=null&&u.next.attrlist!=null)
			rlist=(AttrList) common.Op.copy(u.next.attrlist);
		 u.attrlist=llist;
		if(u.attrlist==null)
			u.attrlist=rlist;
		else{
			AttrList tmplist=u.attrlist;
			while(tmplist.next!=null)
				tmplist=tmplist.next;
			tmplist.next=rlist;
		}
		return u;
	}

	public boolean typeCheck(Type type,ConstValue v)
	{
		if(!(v instanceof ConstValueNull 
				||(type.type==Type.INT&&( v instanceof ConstValueInt))
				||(type.type==Type.BOOL&&( v instanceof ConstValueBoolean))
				||(type.type==Type.CHAR&&( v instanceof ConstValueString)))
				||(type.type==Type.FLOAT&&(v instanceof ConstValueFloat)))
		{
			putError("type not compatible:"+this.getType(type.type)+" vs "+this.getType(v),-1);
			return false;
		}
		return true;
	}
	 
	public Relation transExp(InsertExp e) throws Exception
	{
		if(!checkDB(e)) 
			return null;
		if(!DBInfo.DbMani.CheckPrio(e.name.toString(),env.user, UserPrio.INSERT))
		{
			putError("the user has no priority to insert table:"+e.name,e.pos);
			return null;
		}
		/*sort the namelist according to the defination */
		NameList namelist=e.namelist;
		String tablename=e.name.toString();
		AttrList attrlist=DBInfo.DbMani.getAttriList(env.database,tablename);
		if(attrlist==null)
		{
			putError("table:"+tablename+" not found.",e.pos);
			return null;
		}
		if(namelist==null)
		{
			AttrList tmp=attrlist;
			while(tmp!=null){
				if(tmp.attr.name!=null&&!tmp.attr.name.equals(""))
					namelist=new NameList(-1,new Symbol(tmp.attr.name),namelist);
				tmp=tmp.next;
			}
		}
		/*type check */
		
		if(e.select==null)
		{
			NameList tmplist=namelist;
			ConstValueList valuelist =e.constvalue;
			while(tmplist!=null&&valuelist!=null)
			{
				AttrList tmp=attrlist;
				while(tmp!=null)
				{
					if(tmp.attr.name!=null&&tmp.attr.name.equals(tmplist.name.toString()))
						break;
					tmp=tmp.next;
				}
				if(tmp==null)
				{
					putError("can not find the name:"+tmplist.name.toString(),e.pos);
					return null;
				}
				
				Type type=tmp.attr.type;
				if(!(valuelist.value instanceof ConstValueNull 
						||(type.type==Type.INT&&(valuelist.value instanceof ConstValueInt))
						||(type.type==Type.BOOL&&(valuelist.value instanceof ConstValueBoolean))
						||(type.type==Type.CHAR&&(valuelist.value instanceof ConstValueString))
						||(type.type==Type.FLOAT&&(valuelist.value instanceof ConstValueFloat))))
				{
					putError("type not compatible:"+this.getType(type.type)+" vs "+this.getType(valuelist.value),e.pos);
					return null;
				}
				tmplist=tmplist.next;
				valuelist=valuelist.next;
			}
			if(tmplist!=null||valuelist!=null)
			{
				putError("type numbers not compatible",e.pos);
				return null;
			}			
			return new Insert(tablename,namelist,e.constvalue);
		}
		else {
			Relation sele=transExp(e.select);
			AttrList selectattr=sele.attrlist;
			if(selectattr==null){
				putError("insert value is empty",e.pos);
				return null;
			}
			NameList tmplist=namelist;
			while(tmplist!=null&&selectattr!=null)
			{
				AttrList tmp=attrlist;
				while(tmp!=null)
				{
					if(tmp.attr.name!=null&&tmp.attr.name.equals(tmplist.name.toString()))
						break;
					tmp=tmp.next;
				}
				if(tmp==null)
				{
					putError("can not find the name:"+tmplist.name.toString(),e.pos);
					return null;
				}
				Type type=tmp.attr.type;
				if(type.type!=selectattr.attr.type.type)
				{
					putError("type not compatible :"+this.getType(type.type)+" vs "+this.getType(selectattr.attr.type.type),e.pos);
					return null;
				}				
				tmplist=tmplist.next;
				selectattr=selectattr.next;
			}
			if(tmplist!=null||selectattr!=null)
			{
				putError("parameter numbers not compatible",e.pos);
				return null;
			}
			return new Insert(tablename,namelist,sele);
		}
			 
	}
	public String getType(int t){
		if(t==Type.BOOL)
			return "bool";
		if(t==Type.CHAR)
			return "String";
		if(t==Type.INT)
			return "int";
		if(t==Type.FLOAT)
			return "float";
		return "unknown type";
	}
	public String getType(ConstValue cv){
		if(cv instanceof ConstValueInt)
			return "int";
		if(cv instanceof ConstValueBoolean)
			return "bool";
		if(cv instanceof ConstValueFloat)
			return "float";
		if(cv instanceof ConstValueString)
			return "String";
		return "unknown type of const value:"+cv.getValue();
	}
	public RelaList transSQLs(SQLList e) throws Exception
	{ 
		if(e==null)
			return null;
		Relation r1=transExp(((SQLList) e).first);
		RelaList ne=transSQLs(((SQLList) e).next);
		return new RelaList(r1,ne);
	}
	public Relation transExp(CreateExp ce) throws Exception
	{
 
		if(ce instanceof CreateDatabaseExp)
		{
			CreateDatabaseExp cte=(CreateDatabaseExp)ce;
			if(DBInfo.DbMani.getDB(cte.name))
			{
				putError("create dababas error: "+cte.name+" already exists!",cte.pos);
				return null;
			}
			env.database=cte.name.toString();
			return new CreateDB(cte.name+"");
		}

		if(!checkDB(ce)) 
			return null;
		if(ce instanceof CreateTableExp)
		{
			CreateTableExp cte =(CreateTableExp)ce;
			AttrList list= DBInfo.DbMani.getAttriList(env.database,cte.name.toString());
			if(list!=null)
			{
				putError("create table error :"+cte.name+" already exists!",cte.pos);
				return null;
			}
			list=null;
			CreateElementList el=cte.element;
			int keynum=0;
			while(el!=null)
			{
				CreateElement cre=el.first;
				if(cre instanceof ColumnDefinition)
				{
					ColumnDefinition cd=(ColumnDefinition)cre;
					Type type = null;
					if(cd.type instanceof NameTy){
						if( ((NameTy)cd.type).ty.toString().equalsIgnoreCase("int"))
							type=new Type(Type.INT);
						else if(((NameTy)cd.type).ty.toString().equalsIgnoreCase("boolean"))
							type=new Type(Type.BOOL);
						else if(((NameTy)cd.type).ty.toString().equalsIgnoreCase("float"))
							type=new Type(Type.FLOAT);
					} 
					else if( cd.type instanceof ArrayTy)
					{
						type=new Type(Type.CHAR,((ArrayTy)cd.type).length);
					} 
					boolean not_null;
					if(cd.null_or_not!=null&&cd.null_or_not.toString().equals("NOTNULL"))
						not_null=true;
					else not_null=false;
					ConstValue defaultValue=cd.defaultvalue;
					boolean auto_incre;
					if(cd.auto_increment_or_not!=null&&cd.auto_increment_or_not.toString().equals("AUTOINCREMENT"))
						auto_incre=true;
					else auto_incre=false;
					boolean key;
					if(cd.key_or_not!=null)
					{
						key=true;
						if(keynum!=0){
							putError("only one colum can be a key.",ce.pos);
							return null;
						}
						keynum++;						
						//add key list;
						
					}
					else 
						key=false;
					Attr att=new Attr(cte.name.toString(),cd.name.toString(),type,not_null,defaultValue,auto_incre,key);
					AttrList tmpList=list;
					
					while(tmpList!=null)
					{
						if(tmpList.attr.name.equals(att.name))
						{
							putError("double defined column :"+att.name,cte.pos);
							return null;
						} 
						tmpList=tmpList.next;
					} 
					list=new AttrList(att,list);
				} 
				el=el.next;
			}
			el=cte.element;
			while(el!=null)
			{
				CreateElement cre=el.first;
				if(cre instanceof CheckDef)
				{
					CheckDef cd=(CheckDef)cre;
					ColNameList col_name_list=getColNameList(cd.boolexp);
					while(col_name_list!=null)
					{
						AttrList tmpList=list;
						boolean flag=false;
						while(tmpList!=null)
						{
							Attr a=tmpList.attr;
							if(a.name.equals(col_name_list.name.col.toString()))
							{
								flag=true;
								break;
							}
							tmpList=tmpList.next;
						}
						if(!flag)
						{
							putError(col_name_list.name.col.toString()+" not defined!",cte.pos);
							return null;
						}
						col_name_list=col_name_list.next;
					}
					list=new AttrList(new Attr(cd.boolexp),list);
				}
				else if(cre instanceof PrimaryKeyDef )
				{
					NameList namelist=((PrimaryKeyDef)cre).keylist;
					boolean flag=false;
					while(namelist!=null)
					{
						AttrList tmpList=list;
						while(tmpList!=null)
						{
							Attr attr=tmpList.attr;
							if(attr.name!=null&&attr.name.equals(namelist.name.toString()))
							{
								if(attr.key)
								{
									putError("error defining primary key  :"+attr.name,0);
									return null;
								}
								attr.key=true;
								flag=true;
								break;
							}
							tmpList=tmpList.next;
						}
						if(!flag)
						{
							putError("error defining primary key :"+namelist.name.toString(),0);
							return null;
						}
						namelist=namelist.next;
					}
				}
				else if(cre instanceof ForeignKeyDef)
				{
					ForeignKeyDef fkd=(ForeignKeyDef)cre;
					//check locak fk;
					String fd=fkd.col.toString();
					AttrList tmpList=list;
					while(tmpList!=null)
					{
						if(tmpList.attr.name.equals(fd))
						{
							tmpList.attr.fk=true;
							break;
						}
						tmpList=tmpList.next;
					}
					//check fk to be a pk;
					AttrList fklist=DBInfo.DbMani.getAttriList(env.database,fkd.fk.table.toString());
					boolean flag=false;
					while(fklist!=null)
					{
						if(fklist.attr.name!=null&&fklist.attr.name.equals(fkd.fk.col.toString()))
						{
							if(fklist.attr.key)
							{
								if(fklist.attr.type.type!=tmpList.attr.type.type){
									putError("fk:"+fkd.fk.toString()+" has different type with "+tmpList.attr.name,-1);
									return null;
								}
								flag=true;
								break;
							}
							
						}
						fklist=fklist.next;
					}
					if(!flag)
					{
						putError("fk:"+fkd.fk.toString()+" is not a primary key.",fkd.pos);
						return null;
					}
					tmpList.attr.foreign_key=new ColName(fkd.fk.table.toString(),fkd.fk.col.toString());
					tmpList.attr.fk=true;
				}
				el=el.next;
			}
			
			return new CreateTable(env.database,cte.name.toString(),list); 
		}
		if(ce instanceof CreateViewExp)
		{
			CreateViewExp cve=(CreateViewExp)ce;
			/*need to check select ,which need to be implemented latter*/
			ViewList viewlist=DBInfo.DbMani.getViewList(env.database);
			while(viewlist!=null){
				if(viewlist.view.name.equals(cve.name.toString())){
					putError("create view error:a view with the name:"+cve.name+" has already exists.",cve.pos);
					return null;
				}
				viewlist=viewlist.next;
			}
			Relation relation=transExp(cve.select);
			if(relation==null)
			{
				putError("create view error:(select exp not ok)",ce.pos);
				return null;
			}
			return new CreateView(cve.name.toString(),cve.select);
		}
		if(ce instanceof CreateIndexExp)
		{
			CreateIndexExp cie=(CreateIndexExp)ce;
			AttrList list=DBInfo.DbMani.getAttriList(env.database,cie.getTable_name().toString());
			IndexList indexlist=DBInfo.DbMani.getIndexList(env.database);
			File file=new File(DBInfo.DbMani.rootpath+env.database+"\\"+cie.getTable_name()+".index");
			if(file.exists()){
				putError("a index for table:"+cie.getTable_name()+" has already been decleared.",cie.pos);
				return null;
			}
			for(int i=0;indexlist!=null&&i<indexlist.size();i++)
			{
				 if(indexlist.get(i).table.equals(cie.getTable_name().toString())
						 &&indexlist.get(i).col.equals(cie.getIndex_name().toString())){
					  putError("index has already been decleared",cie.pos);
					  return null;
				 } 
			}
			while(list!=null)
			{
				if(list.attr.name.equals(cie.getCol_name().toString()))
				{
					return new CreateIndex(cie.getIndex_name().toString(),
							cie.getTable_name().toString(),
							cie.getCol_name().toString(),
							cie.isUnique());
				}
				list=list.next;
				
			}
			return null;
		}
		return null;
	}
	public ColNameList getColNameList(BoolExp b)
	{
		ColNameList list=null;
		if(b instanceof BoolAndExp ||(b instanceof BoolOrExp))
		{
			ColNameList list1;
			if(b instanceof BoolAndExp )
			{
				list=getColNameList(((BoolAndExp)b).exp1);
				list1=getColNameList(((BoolAndExp)b).exp2);	
			}
			else
			{
				list=getColNameList(((BoolOrExp)b).exp1);
				list1=getColNameList(((BoolOrExp)b).exp2);
			}
			ColNameList tmp=list;
			if(list==null)
				list=list1;
			else{
				while(tmp.next!=null)
					tmp=tmp.next;
				tmp.next=list1;
			}
			 
		}
		
		if(b instanceof CompBoolExp)
		{
			CompBoolExp cb=(CompBoolExp)b;
			if(cb.v1 instanceof ColValue)
			{
				list=new ColNameList(((ColValue)cb.v1).name,list);
			}
			if(cb.v2 instanceof ColValue)
			{
				list=new ColNameList(((ColValue)cb.v2).name,list);
			}
		}
		if(b instanceof InExp)
		{
			InExp ie=(InExp)b;
			if(ie.value instanceof ColValue)
			{
				list=new ColNameList(((ColValue)ie.value).name,list);
			}
		}
		if(b instanceof LikeEscapeExp)
		{
			LikeEscapeExp ie=(LikeEscapeExp)b;
			if(ie.value instanceof ColValue)
			{
				list=new ColNameList(((ColValue)ie.value).name,list);
			}
		}
		if(b instanceof AllExp)
		{
			AllExp ie=(AllExp)b;
			if(ie.value instanceof ColValue)
			{
				list=new ColNameList(((ColValue)ie.value).name,list);
			}
		}
		if(b instanceof AnyExp)
		{
			AnyExp ie=(AnyExp)b;
			if(ie.value instanceof ColValue)
			{
				list=new ColNameList(((ColValue)ie.value).name,list);
			}
		}
		return list;
	}
}
