package Execute;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
 

import Absyn.*;
import Alge.*;
import DBInfo.View;
import DBInfo.ViewList;
import ErrorMsg.ErrorList;
import ErrorMsg.ErrorMsg;
import Index.HashIndex;
import Symbol.Symbol;
import semant.Env;
import semant.Semant;

public class Execute {
	Env env;
	ErrorList errorlist;
	public Execute(Env env) {
		this.env = env;
		errorlist=null;
	}
	public boolean hasError()
	{
		return errorlist!=null;
	}
	public void printError()
	{
		ErrorList tmp=errorlist;
		if(tmp==null)
			return ;
		System.out.print("*************EXE*****ERROR**********************\n");
		while(tmp!=null)
		{
			System.out.print(tmp.msg);
			System.out.print("\n");
			tmp=tmp.next;
		}
		System.out.print("*********************ERROR*END******************\n");
	}
	public String execute(RelaList list)
	{
		if(list==null)
			return "";
		Relation  r1=execute(list.first);
		String str1;
		if(r1==null)
			str1="";
		else
			str1=r1.results+"\n";			
		return str1+execute(list.next);		
	}
	public void putError(String msg,int loc)
	{
		errorlist=new ErrorList(new ErrorMsg( loc,msg),errorlist);
	}
	public Relation execute(Gamma r)
	{
		if(r.sub instanceof Project &&((Project)r.sub).group_col_name!=null)
		{
			r.attrlist=r.sub.attrlist;
			r.results=r.sub.results;
			return r;
		}
		String [] rows=r.sub.results.split(";");
		//
		return null;
	}
	public Relation execute(Relation r)
	{ 
		try{
		if(r instanceof CreateDB)
			return execute((CreateDB)r);			
		if(r instanceof CreateIndex)
			return execute((CreateIndex)r);
		if(r instanceof CreateTable)
			return execute((CreateTable)r);
		if(r instanceof CreateView)
			return execute((CreateView)r);
		if(r instanceof Alter)
			return execute((Alter)r);
		if(r instanceof Delete)
			return execute((Delete)r);
		if(r instanceof Drop)
			return execute((Drop)r);
		if(r instanceof Insert)
			return execute((Insert)r);
		if(r instanceof UseDB)
			return execute((UseDB)r);
		if(r instanceof Update)
			return execute((Update)r);
		if(r instanceof Describe)
			return execute((Describe)r);
		if(r instanceof Sigma)
			return execute((Sigma)r);
		if(r instanceof Project)
			return execute((Project)r);
		if(r instanceof RealRelation)
			return execute((RealRelation)r);
		if(r instanceof CrossJoin)
			return execute((CrossJoin)r);
		}
		catch(Exception e)
		{
			putError(e.getMessage(),-1);
		}
		return null;		
	}
	public Relation execute(UseDB r)
	{
		env.database=r.dbname;
		r.results="database changed to "+r.dbname+".";
		return r;
		
	}
	public Relation execute(RealRelation real)
	{
		AttrList attrlist=DBInfo.DbMani.getAttriList(env.database,real.tablename);
		real.attrlist=null;
		while(attrlist!=null)
		{
			real.attrlist=new AttrList(attrlist.attr,real.attrlist);
			attrlist=attrlist.next;
		}
		real.results=DBInfo.DbMani.readFile(env.database, real.tablename); 
		return real;
	}
	public Relation execute(CrossJoin crossjoin)
	{
		if(crossjoin==null)
			return null;
		Relation left=this.execute(crossjoin.leftR);
		if(crossjoin.next==null)
		{
			crossjoin.results=left.results;
			crossjoin.attrlist=left.attrlist;
			return crossjoin;
		}
		Relation right=this.execute(crossjoin.next);
		String [] leftrows=left.results.split(";");
		String [] rightrows=right.results.split(";");
		String result="";
		for(int i=0;i<leftrows.length;i++)
		{
			String leftrow=leftrows[i];
			for(int j=0;j<rightrows.length;j++)
			{
				result+=leftrow+rightrows[j]+";";
			}
			
		}
		crossjoin.results=result;
		AttrList llist = null,rlist = null;
		if(crossjoin.leftR!=null&&crossjoin.leftR.attrlist!=null)
			llist=(AttrList) common.Op.copy(crossjoin.leftR.attrlist);
		if(crossjoin.next!=null&&crossjoin.next.attrlist!=null)
			rlist=(AttrList) common.Op.copy(crossjoin.next.attrlist);
		 crossjoin.attrlist=llist;
		if(crossjoin.attrlist==null)
			crossjoin.attrlist=rlist;
		else{
			AttrList tmplist=crossjoin.attrlist;
			while(tmplist.next!=null)
				tmplist=tmplist.next;
			tmplist.next=rlist;
		}
		return crossjoin;
	}
	public Relation execute(Union union)
	{
		return null;
	}
	public Relation execute(Sigma sigma) 
	{
		AttrList attrlist=this.execute(sigma.relation).attrlist;
		List<Attr> list=new ArrayList<Attr>();
		while(attrlist!=null)
		{
			list.add(attrlist.attr);
			attrlist=attrlist.next;
		}
		String[] rows=sigma.relation.results.split(";");
		String result="";
		for(int i=0;i<rows.length;i++)
		{
			
			if(sigma.condition==null||this.calBoolExp(sigma.condition.boolExp, list, rows[i].split(",")))
			{
				result+=rows[i]+";";
			}			
		}
		sigma.attrlist=(AttrList) common.Op.copy(sigma.relation.attrlist);
		sigma.results=result;
		return sigma; 
	}
	
	public Relation execute(Project project) throws Exception
	{
		Relation sub=this.execute(project.sub);
		String[] rows=sub.results.split(";");
		String result="";
		List<Integer> seq=new ArrayList<Integer>();		
		SelectExpr select_expr=project.select_expr;
		AttrList outlist=null;
		List<Attr> outlist1=new ArrayList<Attr>();
		 
		AttrList attrlist=project.sub.attrlist;
		List<Attr> attrlist1=new ArrayList<Attr>();
		while(attrlist!=null)
		{
			attrlist1.add(attrlist.attr);
			attrlist=attrlist.next;
		}
		while(select_expr!=null)
		{
			Attr tmpAttr = null;
			if(select_expr.value instanceof ColValue)
			{
				ColName colname=((ColValue)select_expr.value).name;
				if(colname.col.toString().equals("*"))
				{ 
					for(int i=0;i<attrlist1.size();i++){
						outlist1.add(attrlist1.get(i));
						//seq.add(i);
					}
					seq.add(-2);
					break;
				}
				else
					for(int i=0;i<attrlist1.size();i++)
					{
						if(colname.col.equals(attrlist1.get(i).name))
						{
							seq.add(i);
							tmpAttr=attrlist1.get(i);
							break;
						}
					}				
			}
			else if(select_expr.value instanceof ConstValue)
			{
				seq.add(-1);
				tmpAttr=new Attr("",null,false,null,false,false);
			}
			else if(select_expr.value instanceof FuncValue)
			{
				ColName colname=((FuncValue)select_expr.value).colname;
				for(int i=0;i<attrlist1.size();i++)
				{
					if(colname.col.equals(attrlist1.get(i)))
					{
						seq.add(i);
						break;
					}
				}
				tmpAttr=new Attr(this.transFunc(((FuncValue)select_expr.value).functy,
						colname),null,false,null,false,false); 
				
			}
			else
				throw new Exception("uknow type in select expr");
			outlist1.add(tmpAttr);
			select_expr=select_expr.next;
		}
		for(int i=outlist1.size()-1;i>=0;i--)
		{
			outlist=new AttrList(outlist1.get(i),outlist);
		}
		if(project.group_col_name!=null)
		{
			int group_seq=-1;
			ColName colname = project.group_col_name;
			for (int i = 0; i < attrlist1.size(); i++) {
				if (colname.col.equals(attrlist1.get(i))) {
					group_seq = i;
					break;
				}
			}
			TreeMap<String,List<Object>>  index=new TreeMap<String,List<Object>>();
			//List<Integer> count=new ArrayList<Integer>();
			TreeMap<String ,List<Integer>> aggre=new TreeMap<String,List<Integer>>();
			for(int i=0;i<rows.length;i++)
			{
				String [] cols=rows[i].split(",");
				select_expr=project.select_expr;
				List<Object> obs=new ArrayList<Object>();
				List<Integer> agrlist=new ArrayList<Integer>();//1:sum;2:min;3:max 4:count;
				if(!index.containsKey(cols[group_seq]))
				{ 
					for(int j=0;j<seq.size();j++,select_expr=select_expr.next)
					{
						if(select_expr.value instanceof ColValue)
						{
							if(((ColValue)select_expr.value).name.col.toString().equals("*")){
								for(int s=0;s<attrlist1.size();s++){
									obs.add(cols[s]);
								} 
							}
							else
								obs.add(cols[seq.get(j)]);
						}
						else if(select_expr.value instanceof ConstValue)
							obs.add(((ConstValue)select_expr.value).getValue());
						else if (select_expr.value instanceof FuncValue)
						{
							obs.add(cols[seq.get(j)]);
							if(attrlist1.get(seq.get(j)).type.type!=Type.INT)
							{
								throw(new Exception("func value:"+((FuncValue)select_expr.value).functy.toString()+" should be a integer"));
							}
							if(!cols[seq.get(j)].equalsIgnoreCase("null")){
								agrlist.add(Integer.valueOf(cols[seq.get(j)]));
								agrlist.add(Integer.valueOf(cols[seq.get(j)]));
								agrlist.add(Integer.valueOf(cols[seq.get(j)]));
								agrlist.add(1);
							}
						}
					}
					index.put(cols[group_seq], obs);
					aggre.put(cols[group_seq], agrlist);
				}
				else{
					for(int j=0;j<seq.size();j++,select_expr=select_expr.next)
					{
						if(select_expr.value instanceof ColValue)
						{
							obs.add(cols[seq.get(j)]);
						}
						else if(select_expr.value instanceof ConstValue)
							obs.add(((ConstValue)select_expr.value).getValue());
						else if (select_expr.value instanceof FuncValue)
						{
							obs.add(cols[seq.get(j)]);
							if(attrlist1.get(seq.get(j)).type.type!=Type.INT)
							{
								throw(new Exception("func value:"+((FuncValue)select_expr.value).functy.toString()+" should be a integer"));
							}
							if(!cols[seq.get(j)].equalsIgnoreCase("null")){
								Integer tmp=Integer.valueOf(cols[seq.get(j)]);
								agrlist=aggre.get(cols[group_seq]);
								agrlist.set(0, tmp+agrlist.get(0));
								if(tmp<agrlist.get(1))
									agrlist.set(1, tmp);
								if(tmp<agrlist.get(2))
									agrlist.set(2, tmp);
								agrlist.set(3, agrlist.get(3));
							}
						}
					}
					index.put(cols[group_seq], obs);
					aggre.put(cols[group_seq], agrlist);
				} 
			}
			String result1 ="";
			while(index.size()!=0)
			{
				String key=index.firstKey();

				List<Object> obs=index.get(key);
				List<Integer> agr=aggre.get(key);
				select_expr=project.select_expr;
				
				for(int i=0;i<seq.size();i++,select_expr=select_expr.next)
				{
					if(select_expr.value instanceof FuncValue)
					{
						if(((FuncValue)select_expr.value).functy.toString().equalsIgnoreCase("max"))
						{
							result1+=agr.get(2);
						}else if(((FuncValue)select_expr.value).functy.toString().equalsIgnoreCase("min"))
						{
							result1+=agr.get(1);
						}else if(((FuncValue)select_expr.value).functy.toString().equalsIgnoreCase("sum"))
						{
							result1+=agr.get(0);
						}else if(((FuncValue)select_expr.value).functy.toString().equalsIgnoreCase("count"))
						{
							result1+=agr.get(3);
						}else if(((FuncValue)select_expr.value).functy.toString().equalsIgnoreCase("avg"))
						{
							result1+=agr.get(2)/agr.get(3);
						}
					}
					else{
						result1+=obs.get(i);
					}
					result1+="'";
				}
				result1+=";"; 
				index.remove(key);
			}
			project.results=result1;
			project.attrlist=outlist;
			return project;
		}
		else
		{
			String result1="";
			Integer max,min,sum,count;
			for(int i=0;i<rows.length;i++)
			{
				String[] cols=rows[i].split(",");
				select_expr=project.select_expr;
				for(int j=0;j<seq.size();j++,select_expr=select_expr.next)
				{
					if(select_expr.value instanceof ColValue)
					{
						if(((ColValue)select_expr.value).name.col.toString().equals("*")){
							result1+=rows[i].substring(0, rows[i].length()-1);
						}						
						else
							result1+=cols[seq.get(j)];
					}
					else if(select_expr.value instanceof ConstValue){
						result1+=((ConstValue)select_expr.value).getValue();
					}
					else if(select_expr.value instanceof FuncValue){//max,min
						throw new Exception("not implemented yet");
					}
					else
						throw new Exception("unknown select expr");
					result1+=",";
				}
				result1+=";";
			}
			project.results=result1;
			project.attrlist=outlist;
			return project;
		}
		 
	}
	public String transFunc(Symbol func,ColName colname)
	{
		
		return func.toString()+"("+colname.col+")";
		
	}
	public Relation execute(Describe des)
	{
		NameList namelist=des.names;
		String result="";
		while(namelist!=null)
		{
			AttrList attrlist=DBInfo.DbMani.getAttriList(env.database, namelist.name.toString());
			List<Attr> list=new ArrayList<Attr>();
			while(attrlist!=null)
			{
				list.add(attrlist.attr);
				attrlist=attrlist.next;
			}
			result+="Field\t| Type\t| Null\t| Key\t|Default\t|Extra\n";
			for(int i=list.size()-1;i>=0;i--)
			{
				Attr attr=list.get(i);
				result+=attr.name+"	|";
				if(attr.type.type==Type.INT) result+="INT";
				if(attr.type.type==Type.CHAR) result+="CHAR("+attr.type.size+")";
				if(attr.type.type==Type.BOOL)result+="BOOLEAN";
				result+="\t|";
				if(attr.not_null) result+="not null	|";
				else result+="null	|";
				if(attr.key) result+="PRIMARY	|";
				else result+="	|";
				if(attr.defaultValue!=null)
					result+=attr.defaultValue.getValue();
				else
					result+="\t";
				result+="	|";
				if(attr.auto_incre)
					result+="auto-incre";
				result+="\n";
				 
			} 
			namelist=namelist.next;
		}
		des.results=result;
		return des;		
	}
	public Relation execute(Delete d)
	{
		try{
			AttrList attrlist=DBInfo.DbMani.getAttriList(env.database, d.name);
			List<Attr> list1=new ArrayList<Attr>();
			int totallength=0;
			while(attrlist!=null)
			{
				list1.add(attrlist.attr);
				Type type=attrlist.attr.type;
				if(type.type ==Type.BOOL)
					totallength+=Type.BOOLSIZE;
				else if(type.type==Type.INT)
					totallength+=Type.INTSIZE;
				else if(type.type==Type.CHAR)
					totallength+=type.size;
				attrlist=attrlist.next;
			}List<Attr> list=new ArrayList<Attr>();
			for(int i=list1.size()-1;i>=0;i--)
				list.add(list1.get(i));
			String src=DBInfo.DbMani.readFile(env.database, d.name);
			String [] rows=src.split(";");
			String result="";
			File file=new File(DBInfo.DbMani.rootpath+env.database+"\\"+d.name+".data");
			file.delete();
			DBInfo.DbMani.createNewTableFile(env.database, d.name);
			if(d.exp==null)
			{  
				d.results="success:"+rows.length+" rows deleted";
				return d;
			}
			int total=0;
			 
			int left=0;
			result="";
			int colnum=0;
			for(int i=0;i<rows.length;i++)
			{
				String[] cols=rows[i].split(",");
				colnum=cols.length;
				if(this.calBoolExp(d.exp, list, cols))
				{
					total++;
				}
				else {
					left++;
					
					result+=rows[i];
					result+=";";
					
					 
				}
			}
			DBInfo.DbMani.write(env.database, d.name,result,0, result.length()+1);//(totallength+colnum+1)*left);
  
			d.results="success+"+total+"rows affected.";
			return d;
		}
		catch(Exception e)
		{
			putError(e.getMessage(),-1);
			e.printStackTrace();
			d.results="error";
			return d;
		}
		
	}
	public boolean calBoolExp(BoolExp boolexp,List<Attr> attrlist,String [] values)
	{
		if(boolexp instanceof AllExp)
			return calBoolExp((AllExp)boolexp, attrlist, values);
		if(boolexp instanceof AnyExp)
			return calBoolExp((AnyExp)boolexp, attrlist, values);
		if(boolexp instanceof BoolAndExp)
		{
			BoolAndExp bae=(BoolAndExp)boolexp;
			return calBoolExp(bae.exp1, attrlist, values)&&calBoolExp(bae.exp2, attrlist, values);
		}
		if(boolexp instanceof BoolOrExp)
		{
			BoolOrExp bae=(BoolOrExp)boolexp;
			return calBoolExp(bae.exp1, attrlist, values)||calBoolExp(bae.exp2, attrlist, values);
		}
		if(boolexp instanceof BoolExsitExp)
			return calBoolExp((BoolExsitExp)boolexp, attrlist, values);
		if(boolexp instanceof CompBoolExp)
			return calBoolExp((CompBoolExp)boolexp, attrlist, values);
		if(boolexp instanceof InExp)
			return calBoolExp((InExp)boolexp, attrlist, values);
		if(boolexp instanceof LikeEscapeExp)
			return calBoolExp((LikeEscapeExp)boolexp, attrlist, values);
		return false;
	}
	public boolean calBoolExp(InExp inexp,List<Attr> attrlist,String [] values)
	{
		
		Semant semant=new Semant(env);
		Relation select=semant.transExp(inexp.select);
		AttrList attrlist1=select.attrlist;
		if(attrlist1==null||attrlist1.next!=null)
		{
			putError("subquery in (in exp)should has only one column.",-1);
			return false;
		}
		boolean isin=false;
		Attr attr=attrlist1.attr;
		if(attr.type.type==Type.INT)
		{
			int vi=this.getIntValue(inexp.value, attrlist, values);
			if(vi==-1)
				return false;
			String[] rows=select.results.split(";");
			for(int i=0;i<rows.length;i++)
			{
				String []cols=rows[i].split(",");
				if(cols.length!=1)
				{
					putError("subquery of ((not)in exp) sholud return only on",-1);
					return false;
				}
				if(vi==Integer.valueOf(cols[0]))
				{
					isin=true;
					break;
				}
			}
			return inexp.in?isin:!isin;
		}
		
		
		return false;
	}
	public boolean calBoolExp(CompBoolExp compexp,List<Attr> attrlist,String [] values)
	{
		/**
		 * 1 op 1||t op t1|| t op 1|| 1 op t|| || max()op 1|| 1 op max || str op str 
		 */
		int type1;
		int type2;
		int vi1;
		int vi2;
		String vs1;
		String vs2;
		if(compexp.v1 instanceof ConstValueInt && compexp.v2 instanceof ConstValueInt)
		{
			type1=Type.INT;
			vi1=((ConstValueInt)compexp.v1).value;
			vi2=((ConstValueInt)compexp.v2).value;
			return this.valueComp(vi1,compexp.comp,vi2);
		}else if(compexp.v1 instanceof ColValue  && compexp.v2 instanceof ColValue )
		{
			int ptr1=-1;
			Attr attr1=null;
			int ptr2=-1;
			Attr attr2=null;
			for(int i=0;i<attrlist.size()&&(ptr1==-1||ptr2==-1);i++)
			{
				if(attrlist.get(i).name.equals(((ColValue)compexp.v1).name.col.toString()))
				{
					ptr1=i;attr1=attrlist.get(i);
				}
				if(attrlist.get(i).name.equals(((ColValue)compexp.v2).name.col.toString()))
				{
					ptr2=i;attr2=attrlist.get(i);
				}
			}
			if(ptr1==-1||ptr2==-1)
			{
				putError("col value not found",-1);
				return false;
			}
			if(attr1.type.type==Type.INT&&attr2.type.type==Type.INT)
			{
				return this.valueComp(Integer.valueOf(values[ptr1]), compexp.comp, Integer.valueOf(values[ptr2]));				
			}
			else if(attr1.type.type==Type.CHAR&&attr2.type.type==Type.CHAR)
			{
				return this.valueComp(values[ptr1], compexp.comp, values[ptr2]);
			}
			else {
				putError("col value type not compatible.",-1);
				return false;
			} 
		}
		else if(compexp.v1 instanceof ColValue  && compexp.v2 instanceof ConstValueInt )
		{
			int ptr1=-1;
			Attr attr1=null; 
			for(int i=0;i<attrlist.size()&&(ptr1==-1 );i++)
			{
				if(attrlist.get(i).name.equals(((ColValue)compexp.v1).name.col.toString()))
				{
					ptr1=i;attr1=attrlist.get(i);
				}
			}
			if(ptr1==-1 )
			{
				putError("col value not found",-1);
				return false;
			}
			if(attr1.type.type!=Type.INT)
			{
				putError("col value not a int (const value is a int num).",-1);
				return false;
			}
			return this.valueComp(Integer.valueOf(values[ptr1]), compexp.comp, ((ConstValueInt)compexp.v2).value);
		}
		else if(compexp.v2 instanceof ColValue  && compexp.v1 instanceof ConstValueInt )
		{
			CompBoolExp boolexp=new CompBoolExp(compexp.v2,compexp.comp,compexp.v1);
			return this.calBoolExp(boolexp, attrlist, values);
		}
		else if(compexp.v1 instanceof ColValue  && compexp.v2 instanceof ConstValueString )
		{
			int ptr1=-1;
			Attr attr1=null; 
			for(int i=0;i<attrlist.size()&&(ptr1==-1 );i++)
			{
				if(attrlist.get(i).name.equals(((ColValue)compexp.v1).name.col.toString()))
				{
					ptr1=i;attr1=attrlist.get(i);
				}
			}
			if(ptr1==-1 )
			{
				putError("col value not found",-1);
				return false;
			}
			if(attr1.type.type!=Type.CHAR)
			{
				putError("col value not a string (const value is a sring num).",-1);
				return false;
			}
			return this.valueComp(values[ptr1], compexp.comp, ((ConstValueString)compexp.v2).value);
		}
		else if(compexp.v2 instanceof ColValue  && compexp.v1 instanceof ConstValueString )
		{
			CompBoolExp boolexp=new CompBoolExp(compexp.v2,compexp.comp,compexp.v1);
			return this.calBoolExp(boolexp, attrlist, values);
		}
		else if(compexp.v1 instanceof ConstValueString && compexp.v2 instanceof ConstValueString)
		{
			 return this.valueComp(((ConstValueString)compexp.v1).value, compexp.comp, ((ConstValueString)compexp.v2).value);
		} 
		vi1=this.getIntValue(compexp.v1, attrlist, values);
		vi2=this.getIntValue(compexp.v2, attrlist, values);
		if(vi1!=-1&&vi2!=-1)
		{
			  this.valueComp(vi1,compexp.comp, vi2);
		}
		putError("unknown problem.",-1);
		return false;	
	}
	public boolean calBoolExp(BoolExsitExp boolexsitexp,List<Attr> attrlist,String [] values)
	{
		Semant semant=new Semant(env);
		Relation select=semant.transExp(boolexsitexp.select);
		String[] rows=select.results.split(";");
		if(rows.length==0)
			return false;		
		return true; 
	}
	public boolean calBoolExp(AllExp  allexp,List<Attr> attrlist,String [] values)
	{
		//attrlist and values have the same numbers of value;
		if(!(allexp.value instanceof ConstValueInt))
		{
			putError("all exp should be a int value.",-1);
			return false;
		} 
		Semant semant=new Semant(env);
		Relation select=semant.transExp(allexp.select);
		String[] rows=select.results.split(";");
		boolean flag=true;
		for(int i=0;i<rows.length;i++)
		{
			String row=rows[i];
			String[] cols=row.split(",");
			if(cols.length!=1)
			{
				putError(" Operand (subquery in All ) should contain 1 column(s)",-1);
				return false;
			}
			if(allexp.value instanceof ConstValueInt)//(attrlist.get(0).type.type==Type.INT)
			{
				int v1=((ConstValueInt)allexp.value).value; 
				int v2=Integer.valueOf(cols[0]);
				flag=valueComp(v1,allexp.comp,v2);
			}
			else if(allexp.value instanceof ConstValueString){				
				flag=valueComp(((ConstValueString)allexp.value).value,allexp.comp,cols[0]);				
			}else if(allexp.value instanceof ColValue)
			{
				ColName colname=((ColValue)allexp.value).name;
				Attr attr =null;
				int ptr=-1;
				for(int j=0;j<attrlist.size();j++)
				{
					if(attrlist.get(j).name.equals(colname.col.toString()))
					{
						ptr=j;
						attr=attrlist.get(j);
					}
				}
				if(ptr==-1)
				{
					putError("colname:"+colname.col.toString()+" not found",-1);
					return false;
				}
				//values[j];
				if(attr.type.type==Type.INT)
				{
					int v1=Integer.valueOf(values[ptr]); 
					int v2=Integer.valueOf(cols[0]);
					flag=valueComp(v1,allexp.comp,v2);
				}
				else if(attr.type.type==Type.CHAR)
				{
					flag=valueComp(values[ptr],allexp.comp,cols[0]);
				}
			}
			else if(allexp.value instanceof OperValue)
			{
				OperValue ov=(OperValue)allexp.value;
				if(!((ov.v1 instanceof ConstValueInt)&&(ov.v2 instanceof ConstValueInt)))
				{
					putError("op value should be int value",-1);
					return false;
				}
				int v10=((ConstValueInt)ov.v1).value;
				int v11=((ConstValueInt)ov.v2).value;
				
				int v2=Integer.valueOf(cols[0]);
				flag=valueComp(calOpvalue(v10,ov.op.toString(),v11),allexp.comp,v2);
				
			}
			if(!flag)
				return flag;
		}
		return true; 
	}
	public int getOpValue(OperValue ov,List<Attr> attrlist,String [] values)
	{
		int x,y;
		x=getIntValue(ov.v1,attrlist,values);
		y=getIntValue(ov.v2,attrlist,values);
		if(x==-1||y==-1)
			return -1;
		return this.calOpvalue(x, ov.op.toString(), y);
	}
	public String getStringValue(Value v,List<Attr> attrlist,String[] values)
	{
		if(v instanceof ConstValueString)
		{
			return ((ConstValueString)v).value;
		}
		if(v instanceof ColValue)
		{
			ColName colname=((ColValue)v).name;
			Attr attr =null;
			int ptr=-1;
			for(int j=0;j<attrlist.size();j++)
			{
				if(attrlist.get(j).name.equals(colname.col.toString()))
				{
					ptr=j;
					attr=attrlist.get(j);
				}
			}
			if(ptr==-1)
			{
				putError("colname:"+colname.col.toString()+" not found",-1);
				return null;
			}
			return values[ptr];
		}
		putError("string value not found.",-1);
		return null;		
	}
	public int getIntValue(Value v,List<Attr> attrlist,String [] values)
	{
		int x;
		if(v instanceof ColValue||v instanceof FuncValue)
		{
			ColName colname=null;
			if(v instanceof ColValue)
				colname=( (ColValue)v).name;
			if(v instanceof FuncValue)
			{
				FuncValue fv=(FuncValue)v;
				String funcname="";
				funcname+=fv.functy.toString()+"(";
				if(fv.colname.table!=null)
					funcname+=fv.colname.table+".";
				funcname+=fv.colname.col+")";
				colname=new ColName("",funcname);
			}
			Attr attr =null;
			int ptr=-1;
			for(int j=0;j<attrlist.size();j++)
			{
				if(attrlist.get(j).name.equals(colname.col.toString()))
				{
					ptr=j;
					attr=attrlist.get(j);
				}
			}
			if(ptr==-1)
			{
				putError("colname:"+colname.col.toString()+" not found",-1);
				return -1;
			}
			if(attr.type.type!=Type.INT)
			{
				putError("value is not a integer",-1);
				return -1;
			}
			return x=Integer.valueOf(values[ptr]);
		}else if(v instanceof ConstValueInt)
		{
			return  ((ConstValueInt)v).value;
		}else if(v instanceof OperValue)
			return  getOpValue((OperValue)v,attrlist,values);
		 putError("unknow error.(inget int value)",-1);
		 return -1;
	}
	public int calOpvalue(int v1,String op,int v2)
	{
		if(op.equals("PLUS"))
			return v1+v2;
		if(op.equals("MINUS"))
			return v1-v2;
		if(op.equals("TIMES"))
			return v1*v2;
		if(op.equals("DIVIDE"))
			return v1/v2;
		if(op.equals("MOD"))
			return v1%v2;
		
		return -1;
	}
	public boolean calBoolExp(AnyExp  anyexp,List<Attr> attrlist,String [] values)
	{
		//attrlist and values have the same numbers of value;
		if(!(anyexp.value instanceof ConstValueInt))
		{
			putError("all exp should be a int value.",-1);
			return false;
		} 
		Semant semant=new Semant(env);
		Relation select=semant.transExp(anyexp.select);
		String[] rows=select.results.split(";");
		boolean flag=false;
		for(int i=0;i<rows.length;i++)
		{
			String row=rows[i];
			String[] cols=row.split(",");
			if(cols.length!=1)
			{
				putError(" Operand (subquery in All ) should contain 1 column(s)",-1);
				return false;
			}
			if(anyexp.value instanceof ConstValueInt)//(attrlist.get(0).type.type==Type.INT)
			{
				int v1=((ConstValueInt)anyexp.value).value; 
				int v2=Integer.valueOf(cols[0]);
				flag=valueComp(v1,anyexp.comp,v2);
			}
			else if(anyexp.value instanceof ConstValueString){				
				flag=valueComp(((ConstValueString)anyexp.value).value,anyexp.comp,cols[0]);				
			}else if(anyexp.value instanceof ColValue)
			{
				ColName colname=((ColValue)anyexp.value).name;
				Attr attr =null;
				int ptr=-1;
				for(int j=0;j<attrlist.size();j++)
				{
					if(attrlist.get(j).name.equals(colname.col.toString()))
					{
						ptr=j;
						attr=attrlist.get(j);
					}
				}
				if(ptr==-1)
				{
					putError("colname:"+colname.col.toString()+" not found",-1);
					return false;
				}
				//values[j];
				if(attr.type.type==Type.INT)
				{
					int v1=Integer.valueOf(values[ptr]); 
					int v2=Integer.valueOf(cols[0]);
					flag=valueComp(v1,anyexp.comp,v2);
				}
				else if(attr.type.type==Type.CHAR)
				{
					flag=valueComp(values[ptr],anyexp.comp,cols[0]);
				}
			}
			else if(anyexp.value instanceof OperValue)
			{
				OperValue ov=(OperValue)anyexp.value;
				if(!((ov.v1 instanceof ConstValueInt)&&(ov.v2 instanceof ConstValueInt)))
				{
					putError("op value should be int value",-1);
					return false;
				}
				int v10=((ConstValueInt)ov.v1).value;
				int v11=((ConstValueInt)ov.v2).value;
				
				int v2=Integer.valueOf(cols[0]);
				flag=valueComp(calOpvalue(v10,ov.op.toString(),v11),anyexp.comp,v2);
				
			}
			if(flag)
				return flag;
		}
		return false; 
	}

	public boolean valueComp(String v1,Symbol comp,String v2)
	{
		String cop=comp.toString();
		if(cop.equals("LT"))
		{
			if(stringComp(v1,v2)==-1)
				return true;
		}
		if(cop.equals("GT"))
		{
			if(stringComp(v1,v2)==1)
				return true;
		}
		if(cop.equals("EQ"))
		{
			if(stringComp(v1,v2)==0)
				return true;
		}
		if(cop.equals("NEQ"))
		{
			if(stringComp(v1,v2)!=0)
				return true;
		}
		if(cop.equals("LE"))
		{
			if(stringComp(v1,v2)<=0)
				return true;
		}
		if(cop.equals("LE"))
		{
			if(stringComp(v1,v2)>=0)
				return true;
		} 
		return false;
	}
	public int stringComp(String v1,String v2)
	{ 
		for(int i=0;i<v1.length()&&i<v2.length();i++)
		{
			if(v1.charAt(i)<v2.charAt(i))
				return -1;
			if(v1.charAt(i)>v2.charAt(i))
				return 1;
		}
		if(v1.length()<v2.length())
			return -1;
		if(v1.length()>v2.length())
			return 1;
		return 0;
	}
	public boolean valueComp(int v1,Symbol comp,int v2)
	{
		String cop=comp.toString();
		if(cop.equals("LT")) 
			if(v1<v2)
				return true; 
		if(cop.equals("GT")) 
			if(v1>v2)
				return true; 
		if(cop.equals("EQ")) 
			if(v1==v2)
				return true; 
		if(cop.equals("NEQ")) 
			if(v1!=v2)
				return true; 
		if(cop.equals("LE")) 
			if(v1<=v2)
				return true; 
		if(cop.equals("LE")) 
			if(v1>=v2)
				return true; 
		return false;		
	}
	public Relation execute(Insert i)
	{
		try{
			File attrfile=new File(DBInfo.DbMani.rootpath+env.database+"\\"+i.tablename+".attr");
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(attrfile));
			AttrList attrlist=(AttrList) ois.readObject();
			//insert const value;
			//List<Object> list = new ArrayList<Object>();
			String result = ""; 
			List<Integer> seq=new ArrayList<Integer>();
			AttrList attrlist1=attrlist;
			List<Attr> attrarray=new ArrayList<Attr>();
			long totallength=0;
			long length=DBInfo.DbMani.getfilesize(env.database, i.tablename);
			while(attrlist1!=null)
			{
				attrarray.add(attrlist1.attr);
				
				Type type=attrlist1.attr.type;
				if(type.type ==Type.BOOL)
					totallength+=Type.BOOLSIZE;
				else if(type.type==Type.INT)
					totallength+=Type.INTSIZE;
				else if(type.type==Type.CHAR)
					totallength+=type.size;
				attrlist1=attrlist1.next;
			}
			for(int mmm=attrarray.size()-1;mmm>=0;mmm--)
			{
				Attr attr=attrarray.get(mmm);//=attrlist1.attr;
				int ptr=0;
				NameList namelist=i.namelist;
				boolean flag=false;
				while(namelist!=null)
				{ 
					if(namelist.name.toString().equals(attr.name))
					{
						flag=true;
						break;
					}
					ptr++;
					namelist=namelist.next;
				}
				if(!flag)
				{
					seq.add(-1);
					if(attr.defaultValue!=null)
					{	ConstValue constvalue=attr.defaultValue;
						 
						if(attr.type.type==Type.BOOL)
							result+=((ConstValueBoolean)constvalue).getValue();
						else if(attr.type.type ==Type.INT)
							result+=((ConstValueInt)constvalue).getValue().toString();
						else if(attr.type.type==Type.CHAR)
						{
							String tmp;
							tmp=((ConstValueString)constvalue).getValue();
							if(tmp.length()<attr.type.size)
							{
								tmp+=" ";
							}
							else
							{
								tmp=tmp.substring(0,attr.type.size);
							}
							result+=tmp;
						} 
						result+=","; 
					}
					else if(attr.not_null){
						putError("insert value:"+attr.name+" shouldnot be null",-1);
						return null;
					}
					else
						result+="null,";
				}
				else
				{
					seq.add(ptr); 
					if(i.constvalue!=null){
						int x=ptr;
						ConstValueList constlist=i.constvalue;
						while(x!=0)
						{
							constlist=constlist.next;
							x--;
						}
						ConstValue constvalue=constlist.value;
						 
						if(attr.type.type==Type.BOOL)
							result+=((ConstValueBoolean)constvalue).getValue();
						else if(attr.type.type ==Type.INT)
							result+=((ConstValueInt)constvalue).getValue().toString();
						else if(attr.type.type==Type.CHAR)
						{
							String tmp;
							tmp=((ConstValueString)constvalue).getValue();
							if(tmp.length()<attr.type.size)
							{
								tmp+=" ";
							}
							else
							{
								tmp=tmp.substring(0,attr.type.size);
							}
							result+=tmp;
						}
						 
						result+=",";
					}
				} 
			}
			//select not yet implemented
			if(i.select!=null)
			{
				String[] rows=i.select.results.split(";");
				for(int j=0;j<rows.length;j++)
				{
					String[] cols=rows[j].split(",");
					if(cols.length<seq.size())
						break;
					for(int s=0;s<seq.size();s++)
					{
						if(seq.get(s)==-1)//use default
						{
							Attr attr=attrarray.get(s);
							ConstValue constvalue=attr.defaultValue; 
							 
							if(attr.type.type==Type.BOOL)
								result+=((ConstValueBoolean)constvalue).getValue();
							else if(attr.type.type ==Type.INT)
								result+=((ConstValueInt)constvalue).getValue().toString();
							else if(attr.type.type==Type.CHAR)
							{
								String tmp;
								tmp=((ConstValueString)constvalue).getValue();
								if(tmp.length()<attr.type.size)
								{
								}
								else
								{
									tmp=tmp.substring(0,attr.type.size);
								}
								result+=tmp;
							} 
							
						}
						else{
							result+=cols[seq.get(s)];
						}
						result+=","; 
					}
					result+=";";
				}
				DBInfo.DbMani.write(env.database, i.tablename,result, length,(totallength+attrarray.size()+1)*rows.length);
			}
			//writetoFile;
			
			if(i.constvalue!=null)
			{
				result+=";";
				DBInfo.DbMani.write(env.database, i.tablename,result, length,totallength+attrarray.size()+1);
			}
			 
				
			 
		}
		catch(Exception e)
		{
			putError(e.getMessage(),-1);
		}
		i.results="insert ok.";
		return i;
	}
	public Relation execute(Update u)
	{
		try{
			//change attr;
			File attrfile=new File(DBInfo.DbMani.rootpath+env.database+"\\"+u.tablename+".attr");
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(attrfile));
			AttrList attrlist=(AttrList) ois.readObject();
			 	
			AttrList tmplist=attrlist;
			int totallength=0; 
			List<Attr> list=new ArrayList<Attr>();
			while(tmplist!=null)
			{ 
				Type type=attrlist.attr.type;
				if(type.type ==Type.BOOL)
					totallength+=Type.BOOLSIZE;
				else if(type.type==Type.INT)
					totallength+=Type.INTSIZE;
				else if(type.type==Type.CHAR)
					totallength+=type.size;
				list.add(tmplist.attr);
				totallength+=1;
				tmplist=tmplist.next;
			}
			Collections.reverse(list);
			List<Integer> seq=new ArrayList<Integer>();
			AssignList assignlist=u.assign;
			List<ConstValue> constlist=new ArrayList<ConstValue>();
			while(assignlist!=null)
			{
				for(int i=0;i<list.size();i++)
				{
					if(assignlist.first.var.toString().equals(list.get(i).name))
					{
						seq.add(i);
						constlist.add((ConstValue)assignlist.first.value);
					}
				}
				assignlist=assignlist.next;
			}
			//change data;
			String src=DBInfo.DbMani.readFile(env.database, u.tablename);
			String result="";
			long index=0;
			String[] rows=src.split(";");
			for(int i=0;i<rows.length;i++)
			{ 
				String[] cols=rows[i].split(",");
				for(int j=0;j<seq.size();j++)
				{
					cols[seq.get(j)]=constlist.get(j).getValue().toString();
				}
				result="";
				for(int j=0;j<cols.length;j++)
				{
					result+=cols[j]+",";
				}
				result+=";";
				DBInfo.DbMani.write(env.database,u.tablename, result, index, totallength);
				index+=totallength+1;
			}
		}catch(Exception e)
		{
			putError(e.getMessage(),-1);			
		}
		return u;	
	}
	public Relation execute(Alter al)
	{ 
		try{
			//change attr;
			File attrfile=new File(DBInfo.DbMani.rootpath+env.database+"\\"+al.tablename+".attr");
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(attrfile));
			AttrList attrlist=(AttrList) ois.readObject();
			if(al.choice==Alter.ADD)
			{
				Attr attr=new Attr(al.colname,al.type,false,null,false,false);
				attrlist=new AttrList(attr,attrlist);
				ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(attrfile));
				oos.writeObject(attrlist);
			}			
			AttrList tmplist=attrlist;
			int totallength=0;
			
			while(tmplist!=null)
			{ 
				Type type=attrlist.attr.type;
				if(type.type ==Type.BOOL)
					totallength+=Type.BOOLSIZE;
				else if(type.type==Type.INT)
					totallength+=Type.INTSIZE;
				else if(type.type==Type.CHAR)
					totallength+=type.size;
				totallength+=1;
				tmplist=tmplist.next;
			}
			//change data;
			String src=DBInfo.DbMani.readFile(env.database, al.tablename);
			String result="";
			long index=0;
			String[] rows=src.split(";");
			for(int i=0;i<rows.length;i++)
			{
				result=rows[i]+"null,";
				DBInfo.DbMani.write(env.database,al.tablename, result, index, totallength+1);
				index+=totallength+1;
			}
			
		}catch(Exception e)
		{
			putError(e.getMessage(),-1);			
		}
		al.results="";
		return al;		
	}
	public Relation execute(CreateIndex ci)
	{ 
		//create index indexname on table (col)
		String [] rows=DBInfo.DbMani.readFile(env.database,ci.table_name).split(";");
		HashIndex hashIndex=new HashIndex(env.database,ci.table_name,ci.col_name,ci.index_name);
		AttrList attrlist=DBInfo.DbMani.getAttriList(env.database,ci.table_name);
		AttrList tmplist=attrlist;
		int ptr=-1;
		while(tmplist!=null)
		{
			ptr++;
			if(tmplist.attr.name.equals(ci.col_name)){
				break;
			}
			tmplist=tmplist.next;
		}
		for(int i=0;i<rows.length;i++)
		{
			hashIndex.addPos(rows[ptr], new Integer(i));
		}
		hashIndex.putData();
		DBInfo.DbMani.addIndexInfo(env.database, ci.table_name, ci.col_name);
		return ci; 
	}
	public Relation execute(CreateDB cdb)
	{
		env.database=cdb.dbname;
		DBInfo.DbMani.createDB(cdb.dbname);
		cdb.results="create database:"+cdb.dbname+" success.";
		return cdb;
	}
	public Relation execute(CreateTable ct)
	{
		//add db info;
		try{
			//write attrlist into file
			File attrfile=new File(DBInfo.DbMani.rootpath+env.database+"\\"+ct.tableName+".attr");
			attrfile.createNewFile();
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(attrfile));
			oos.writeObject(ct.attrs);
			oos.flush();
			oos.close();
	//		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(attrfile));
//			AttrList list=(AttrList) ois.readObject();
			
			DBInfo.DbMani.createNewTableFile(env.database,ct.tableName );
			ct.results="create table :"+ct.tableName;
			return ct;
		}
		catch(Exception e)
		{
			putError(e.getMessage(),-1);
			ct.results="error";
			return ct;
		}
		
	}
	public Relation execute(CreateView cv)
	{
		if(!checkDB())
		{
			cv.results="";
			return cv;		
		}
		try {
			File file=new File(DBInfo.DbMani.rootpath+env.database+"\\view.list");
			ViewList viewlist=null;
			if(!file.exists())
			{	
					file.createNewFile();			
			}
			else
			{
				ObjectInputStream ois=new ObjectInputStream(new FileInputStream(file));
				viewlist=(ViewList)ois.readObject(); 
			}
			viewlist=new ViewList(new View(cv.name,cv.select),viewlist);
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(viewlist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			putError(e.getMessage(),-1);
			cv.results="";
			return cv;	
		}
		cv.results="create view success";
		return cv;	
		 
	}
	boolean checkDB()
	{
		if(env.database==null||env.database.equals(""))
		{	
			putError("database not selected",-1);
			return false;
		}
		return true; 
	}
}
