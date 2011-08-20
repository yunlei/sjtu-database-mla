package semant;

import Absyn.*;
import Alge.*;
 
import ErrorMsg.ErrorList;
import ErrorMsg.ErrorMsg;
import Symbol.Symbol;

public class Semant{
	Env env;
	ErrorList error_list=null;
	public Semant(Env env) {
		this.env = env;
	}
	public void putError(String msg,int loc)
	{
		error_list=new ErrorList(new ErrorMsg( loc,msg),error_list);
	}
	public Relation transExp(Exp e)
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
					putError("table name:"+name+" not implemented.",namelist.pos);
					return null;
				}
			}
			return new Drop(Drop.DROPTABLE,namelist);
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
			putError("drop index not implement.",e.pos);
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
		putError("grant exp not implement.",e.pos);
		return null;
	}
	public Relation transExp(DescribeExp e)
	{
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
		}
		return new Describe(e.names);
				
	}
	public Relation transExp(UpdateExp e)
	{
		//update tablename set asignment where boolexp
		//check tablename
		AttrList attrlist =DBInfo.DbMani.getAttriList(env.database, e.name.toString());
		if(attrlist==null)
		{
			putError("table:"+e.name.toString()+" not found.",e.pos);
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
				Attr attr=attrlist.attr;
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
			}
			if(!flag)
			{
				putError("assign parameter:"+name+" not found.",assign.pos);
				return null;
			}
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
					if(tmplist.attr.name.equals(colname.col))
					{
						flag=true;
						break;
					}
				}
				if(!flag)
				{
					putError("colname:"+colname.col+" not found(in where clause",e.pos);
					return null;
				}
				colnamelist=colnamelist.next;
			}
		}
		 return new Update(new RealRelation(e.name.toString()),e.assign,e.bool);	
	}
	public Relation transExp(DeleteExp e)
	{
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
					if(attr.name!=null||attr.name.equals(colname.col))
					{
						flag=true;
						break;
					}					
				}
				if(!flag)
				{
					putError("colname :"+colname.col+"(in where clause) not found",e.pos);
					return null;
				} 
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
	public Relation transExp(SelectExp s)
	{ 
		Relation table=transFrom(s.fromclause.tablereflist);
		if(s.havingclause!=null&&s.groupclause==null)
		{
			putError("having clause must be appended after group cluase",s.pos);
			return null;
		} 
		//check where select attr is in the table
		SelectExpr select_expr=s.selectexpr;
		while(select_expr!=null)
		{
			if(select_expr.value instanceof ColValue)
			{
				ColName colname=((ColValue)select_expr.value).name;
				if(colname.col.toString().equals("*"))
				{}
				else
				{
					CrossJoin u=(CrossJoin)table;
					boolean flag=false;
					while(u!=null&&!flag)
					{
						if(colname.table==null||colname.table.equals("")||colname.table.equals(u.tablename)
								||(u.asname!=null&&colname.table.equals(u.asname)))
						{ 
							AttrList attlist=u.leftR.attrlist;							
							while(attlist!=null)
							{
								if(attlist.attr.name.equals(colname.col))
								{
									flag=true;
									break;
								}
							}
						}
						u=u.next;
					}
					if(!flag)
					{
						putError("colname not found:"+colname.col,s.pos);
					}
				}
			} 
			select_expr=select_expr.next;
		}
		//check where
		ColNameList colnamelist=getColNameList(s.whereclause.boolexp);
		while(colnamelist!=null)
		{
			ColName colname=colnamelist.name;
			CrossJoin u=(CrossJoin)table;
			if(!colNameInCrossUnion(colname,u))
				return null;
			colnamelist=colnamelist.next;
		}
		//check order list;
		OrderList orderlist=s.orderclause.orderlist;
		while(orderlist!=null)
		{
			ColName colname=orderlist.col;
			CrossJoin u=(CrossJoin)table;
			if(!colNameInCrossUnion(colname,u))
				return null;			
			orderlist=orderlist.next;
		}
		//check group list;
		
		if(s.groupclause!=null&&!colNameInCrossUnion(s.groupclause.name,(CrossJoin)table))
			return null;
		//check having list;
		if (s.havingclause != null) {
			colnamelist = getColNameList(s.havingclause.boolexp);
			while (colnamelist != null) {
				ColName colname = colnamelist.name;
				CrossJoin u = (CrossJoin) table;
				if (!colNameInCrossUnion(colname, u))
					return null;
				colnamelist = colnamelist.next;
			}
		}
		//genegate the relation;
		Relation result;
		result=new Sigma(new Condition(s.whereclause.boolexp),table);
		result=new Project(s.selectexpr,result);
		if(s.groupclause!=null)
			result=new Group(result,s.groupclause.name);
		if(s.havingclause!=null&&(result instanceof Group))
			result=new Sigma(new Condition(s.havingclause.boolexp),result);
		if(s.orderclause!=null)
			result=new Order(result,s.orderclause.orderlist);
		if(s.distinct_or_not!=null)
			result=new Gamma(result);
		return null;		
	}
	public boolean colNameInCrossUnion(ColName colname,CrossJoin u)
	{
		boolean flag=false;
		while(u!=null&&!flag)
		{
			if(colname.table==null||colname.table.equals("")||colname.table.equals(u.tablename)
					||(u.asname!=null&&colname.table.equals(u.asname)))
			{ 
				AttrList attlist=u.leftR.attrlist;							
				while(attlist!=null)
				{
					if(attlist.attr.name.equals(colname.col))
					{
						flag=true;
						break;
					}
				}
			}
			u=u.next;
		}
		if(!flag)
		{
			putError("colname not found  in the where clause:"+colname.col,-1);
		}			
		return flag;
	}
	private CrossJoin transFrom(TableRefList tl) {
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
			r1=new RealRelation(tl.tableref.name.toString()); 
			r1.attrlist=DBInfo.DbMani.getAttriList(env.database, tl.tableref.name.toString());
		}
		if(tl.tableref.subquery!=null)
		{
			r1=transExp(tl.tableref.subquery);			
		}
		if(tl.tableref.asname!=null)
		{
			r1.asname=tl.tableref.asname.toString();
		} 
		u=new CrossJoin(tl.tableref.name.toString(),r1,transFrom(tl.next));
		return u;
	}

	public boolean typeCheck(Type type,ConstValue v)
	{
		if(!(v instanceof ConstValueNull 
				||(type.type==Type.INT&&( v instanceof ConstValueInt))
				||(type.type==Type.BOOL&&( v instanceof ConstValueBoolean))
				||(type.type==Type.CHAR&&( v instanceof ConstValueString))))
		{
			putError("type not compatible",-1);
			return false;
		}
		return true;
	}
	 
	public Relation transExp(InsertExp e)
	{
		/*sort the namelist according to the defination */
		NameList namelist=e.namelist;
		String tablename=e.name.toString();
		AttrList attrlist=DBInfo.DbMani.getAttriList(env.database,tablename);
		if(namelist==null)
		{
			AttrList tmp=attrlist;
			while(tmp!=null){
				if(tmp.attr.name!=null&&!tmp.attr.name.equals(""))
					namelist=new NameList(-1,new Symbol(tmp.attr.name),namelist);
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
						||(type.type==Type.CHAR&&(valuelist.value instanceof ConstValueString))))
				{
					putError("type not compatible",e.pos);
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
			return new Insert(namelist,e.constvalue);
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
				}
				if(tmp==null)
				{
					putError("can not find the name:"+tmplist.name.toString(),e.pos);
					return null;
				}
				Type type=tmp.attr.type;
				if(type.type!=selectattr.attr.type.type)
				{
					putError("type not compatible",e.pos);
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
		}
			
		return null;
	}
	public RelaList transSQLs(SQLList e)
	{ 
		if(e==null)
			return null;
		Relation r1=transExp(((SQLList) e).first);
		RelaList ne=transSQLs(((SQLList) e).next);
		return new RelaList(r1,ne);
	}
	public Relation transExp(CreateExp ce)
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
			while(el!=null)
			{
				CreateElement cre=el.first;
				if(cre instanceof ColumnDefinition)
				{
					ColumnDefinition cd=(ColumnDefinition)cre;
					Type type = null;
					if(cd.type instanceof NameTy){
						if( cd.type.toString().equals("int"))
							type=new Type(Type.INT);
						else if(cd.type.toString().equals("boolean"))
							type=new Type(Type.BOOL);
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
					if(cd.auto_increment_or_not!=null&&cd.auto_increment_or_not.toString().equals("NOTNULL"))
						auto_incre=true;
					else auto_incre=false;
					boolean key;
					if(cd.key_or_not!=null)
						key=true;
					else 
						key=false;
					Attr att=new Attr(cd.name.toString(),type,not_null,defaultValue,auto_incre,key);
					AttrList tmpList=list;
					 
					while(tmpList!=null)
					{
						if(tmpList.attr.name.equals(att.name))
						{
							putError("double defined column :"+att.name,cte.pos);
							return null;
						} 
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
						}
						if(!flag)
						{
							putError(col_name_list.name.col.toString()+" not defined!",cte.pos);
							return null;
						}
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
							if(attr.name.equals(namelist.name.toString()))
							{
								if(!attr.key)
								{
									putError("error defining primary key  :"+attr.name,0);
									return null;
								}
								attr.key=true;
								flag=true;
								break;
							}
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
						if(fklist.attr.name.equals(fkd.fk.col.toString()))
						{
							if(fklist.attr.key)
							{
								flag=true;
								break;
							}
						}
					}
					if(!flag)
					{
						putError("fk:"+fkd.fk.col.toString()+" is not a primary key.",fkd.pos);
						return null;
					}
					tmpList.attr.foreign_key=new ColName(fkd.fk.table.toString(),fkd.fk.col.toString());
				}
				el=el.next;
			}
			
			return new CreateTable(env.database,cte.name.toString(),list); 
		}
		if(ce instanceof CreateViewExp)
		{
			CreateViewExp cve=(CreateViewExp)ce;
			/*need to check select ,which need to be implemented latter*/
			Relation relation=transExp(cve.select);
			if(relation==null)
				return null;
			return new CreateView(cve.name.toString(),cve.select);
		}
		if(ce instanceof CreateIndexExp)
		{
			CreateIndexExp cie=(CreateIndexExp)ce;
			AttrList list=DBInfo.DbMani.getAttriList(env.database,cie.getTable_name().toString());
			while(list!=null)
			{
				if(list.attr.name.equals(cie.getCol_name().toString()))
				{
					return new CreateIndex(cie.getIndex_name().toString(),
							cie.getTable_name().toString(),
							cie.getCol_name().toString(),
							cie.isUnique());
				}
				
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