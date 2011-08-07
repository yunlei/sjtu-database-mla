package Parse;
import ErrorMsg.ErrorMsg;

%%

 
%function nextToken
%type java_cup.runtime.Symbol
%char

%{
 StringBuffer string = new StringBuffer();

 private java_cup.runtime.Symbol nextToken(int kind, Object value) {
	return new java_cup.runtime.Symbol(kind, yychar, yychar+yylength(),value);
}

private java_cup.runtime.Symbol nextToken(int kind){
	return nextToken(kind,yytext());
}
 

%}

%eofval{
	{
      if(yy_state ==STRING) return nextToken(sym.ERROR);
	 else return nextToken(sym.EOF);
        }
%eofval}

digit=[0-9]
notQuote=[^']
doubleQuote=['']

int={digit}+
float={digit}+[.]{digit}+
str={notQuote}*
NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b\012]


%state STRING
%%


<YYINITIAL> "ALTER"       { return nextToken(sym.ALTER); }
<YYINITIAL> "ADD"         { return nextToken(sym.ADD); }
<YYINITIAL> "ALL"         { return nextToken(sym.ALL); }
<YYINITIAL> "AND"         { return nextToken(sym.AND); }
<YYINITIAL> "ANY"         { return nextToken(sym.ANY); }
<YYINITIAL> "AS"          { return nextToken(sym.AS); }
<YYINITIAL> "ASC"         { return nextToken(sym.ASC); }
<YYINITIAL> "BY"          { return nextToken(sym.BY); }
<YYINITIAL> "CREATE"      { return nextToken(sym.CREATE); }
<YYINITIAL> "DELETE"      { return nextToken(sym.DELETE); }
<YYINITIAL> "DESC"        { return nextToken(sym.DESC); }
<YYINITIAL> "DROP"        { return nextToken(sym.DROP); }
<YYINITIAL> "DATABASE"    { return nextToken(sym.DATABASE); }
<YYINITIAL> "ESCAPE"      { return nextToken(sym.ESCAPE); }
<YYINITIAL> "EXISTS"      { return nextToken(sym.EXISTS); }
<YYINITIAL> "FROM"        { return nextToken(sym.FROM); }
<YYINITIAL> "INDEX"       { return nextToken(sym.INDEX); }
<YYINITIAL> "INSERT"      { return nextToken(sym.INSERT); }
<YYINITIAL> "INTO"        { return nextToken(sym.INTO); }
<YYINITIAL> "KEY"         { return nextToken(sym.KEY); }
<YYINITIAL> "LIKE"        { return nextToken(sym.LIKE); }
<YYINITIAL> "NOT"         { return nextToken(sym.NOT); }
<YYINITIAL> "NULL"        { return nextToken(sym.NULL); }
<YYINITIAL> "ON"          { return nextToken(sym.ON); }
<YYINITIAL> "OR"          { return nextToken(sym.OR); }
<YYINITIAL> "ORDER"       { return nextToken(sym.ORDER); }
<YYINITIAL> "PRIMARY"     { return nextToken(sym.PRIMARY); }
<YYINITIAL> "SET"         { return nextToken(sym.SET); }
<YYINITIAL> "SELECT"      { return nextToken(sym.SELECT); }
<YYINITIAL> "TABLE"       { return nextToken(sym.TABLE); }
<YYINITIAL> "UPDATE"      { return nextToken(sym.UPDATE); }
<YYINITIAL> "VIEW"        { return nextToken(sym.VIEW); }
<YYINITIAL> "VALUES"      { return nextToken(sym.VALUES); }
<YYINITIAL> "WHERE"       { return nextToken(sym.WHERE); }
<YYINITIAL> "AUTO"        { return nextToken(sym.AUTO); }
<YYINITIAL> "CHECK"       { return nextToken(sym.CHECK); }
<YYINITIAL> "UNION"       { return nextToken(sym.UNION); }
<YYINITIAL> "USE"         { return nextToken(sym.USE); }
<YYINITIAL> "HAVING"      { return nextToken(sym.HAVING); }
<YYINITIAL> "DEFAULT"     { return nextToken(sym.DEFAULT); }
<YYINITIAL> "GROUP"       { return nextToken(sym.GROUP); }
<YYINITIAL> "INCREMENT"   { return nextToken(sym.INCREMENT); }
<YYINITIAL> "DISTINCT"    { return nextToken(sym.DISTINCT); }
<YYINITIAL> "IN"          { return nextToken(sym.IN); }

<YYINITIAL> "AVG"         { return nextToken(sym.AVG); }
<YYINITIAL> "COUNT"       { return nextToken(sym.COUNT); }
<YYINITIAL> "MIN"         { return nextToken(sym.MIN); }
<YYINITIAL> "MAX"         { return nextToken(sym.MAX); }
<YYINITIAL> "SUM"         { return nextToken(sym.SUM); }

<YYINITIAL> "INT"         { return nextToken(sym.INT); }
<YYINITIAL> "FLOAT"       { return nextToken(sym.FLOAT); }
<YYINITIAL> "CHAR"        { return nextToken(sym.CHAR); }
<YYINITIAL> "BOOLEAN"     { return nextToken(sym.BOOLEAN); }


<YYINITIAL> "alter"       { return nextToken(sym.ALTER); }
<YYINITIAL> "add"         { return nextToken(sym.ADD); }
<YYINITIAL> "all"         { return nextToken(sym.ALL); }
<YYINITIAL> "and"         { return nextToken(sym.AND); }
<YYINITIAL> "any"         { return nextToken(sym.ANY); }
<YYINITIAL> "as"          { return nextToken(sym.AS); }
<YYINITIAL> "asc"         { return nextToken(sym.ASC); }
<YYINITIAL> "by"          { return nextToken(sym.BY); }
<YYINITIAL> "create"      { return nextToken(sym.CREATE); }
<YYINITIAL> "delete"      { return nextToken(sym.DELETE); }
<YYINITIAL> "desc"        { return nextToken(sym.DESC); }
<YYINITIAL> "drop"        { return nextToken(sym.DROP); }
<YYINITIAL> "database"    { return nextToken(sym.DATABASE); }
<YYINITIAL> "escape"      { return nextToken(sym.ESCAPE); }
<YYINITIAL> "exists"      { return nextToken(sym.EXISTS); }
<YYINITIAL> "from"        { return nextToken(sym.FROM); }
<YYINITIAL> "index"       { return nextToken(sym.INDEX); }
<YYINITIAL> "insert"      { return nextToken(sym.INSERT); }
<YYINITIAL> "into"        { return nextToken(sym.INTO); }
<YYINITIAL> "key"         { return nextToken(sym.KEY); }
<YYINITIAL> "like"        { return nextToken(sym.LIKE); }
<YYINITIAL> "not"         { return nextToken(sym.NOT); }
<YYINITIAL> "null"        { return nextToken(sym.NULL); }
<YYINITIAL> "on"          { return nextToken(sym.ON); }
<YYINITIAL> "or"          { return nextToken(sym.OR); }
<YYINITIAL> "order"       { return nextToken(sym.ORDER); }
<YYINITIAL> "primary"     { return nextToken(sym.PRIMARY); }
<YYINITIAL> "set"         { return nextToken(sym.SET); }
<YYINITIAL> "select"      { return nextToken(sym.SELECT); }
<YYINITIAL> "table"       { return nextToken(sym.TABLE); }
<YYINITIAL> "update"      { return nextToken(sym.UPDATE); }
<YYINITIAL> "view"        { return nextToken(sym.VIEW); }
<YYINITIAL> "values"      { return nextToken(sym.VALUES); }
<YYINITIAL> "where"       { return nextToken(sym.WHERE); }
<YYINITIAL> "auto"        { return nextToken(sym.AUTO); }
<YYINITIAL> "check"       { return nextToken(sym.CHECK); }
<YYINITIAL> "union"       { return nextToken(sym.UNION); }
<YYINITIAL> "use"         { return nextToken(sym.USE); }
<YYINITIAL> "having"      { return nextToken(sym.HAVING); }
<YYINITIAL> "default"     { return nextToken(sym.DEFAULT); }
<YYINITIAL> "group"       { return nextToken(sym.GROUP); }
<YYINITIAL> "increment"   { return nextToken(sym.INCREMENT); }
<YYINITIAL> "distinct"    { return nextToken(sym.DISTINCT); }
<YYINITIAL> "in"          { return nextToken(sym.IN); }

<YYINITIAL> "avg"         { return nextToken(sym.AVG); }
<YYINITIAL> "count"       { return nextToken(sym.COUNT); }
<YYINITIAL> "min"         { return nextToken(sym.MIN); }
<YYINITIAL> "max"         { return nextToken(sym.MAX); }
<YYINITIAL> "sum"         { return nextToken(sym.SUM); }

<YYINITIAL> "int"         { return nextToken(sym.INT); }
<YYINITIAL> "float"       { return nextToken(sym.FLOAT); }
<YYINITIAL> "char"        { return nextToken(sym.CHAR); }
<YYINITIAL> "boolean"     { return nextToken(sym.BOOLEAN); }

<YYINITIAL> "true"        { return nextToken(sym.TRUE); }
<YYINITIAL> "false"       { return nextToken(sym.FALSE); }
<YYINITIAL> "TRUE"        { return nextToken(sym.TRUE); }
<YYINITIAL> "FALSE"       { return nextToken(sym.FALSE); }

<YYINITIAL> [a-zA-Z]([0-9a-zA-Z]|_)*     { return nextToken(sym.NAME,yytext()); }

<YYINITIAL> {int}                        { return nextToken(sym.INTVALUE,new Integer(yytext())); }
<YYINITIAL> {float}                      { return nextToken(sym.FLOATVALUE,new Float(yytext())); }

<YYINITIAL> {NONNEWLINE_WHITE_SPACE_CHAR}+ { }

<YYINITIAL> \r\n { }
<YYINITIAL> \n { }


<YYINITIAL> \'                 { string.setLength(0);yybegin(STRING); }

 
<YYINITIAL> "="                  { return nextToken(sym.EQ); }
<YYINITIAL> "<>"                 { return nextToken(sym.NEQ); }
<YYINITIAL> "<"                  { return nextToken(sym.LT); }
<YYINITIAL> ">"                  { return nextToken(sym.GT); }
<YYINITIAL> "<="                 { return nextToken(sym.LE); }
<YYINITIAL> ">="                 { return nextToken(sym.GE); }
<YYINITIAL> "+"                  { return nextToken(sym.PLUS); }
<YYINITIAL> "-"                  { return nextToken(sym.MINUS); }
<YYINITIAL> "*"                  { return nextToken(sym.TIMES); }
<YYINITIAL> "/"                  { return nextToken(sym.DIVIDE); }
<YYINITIAL> "%"                  { return nextToken(sym.MOD); }


<YYINITIAL> ","                  { return nextToken(sym.COMMA); }
<YYINITIAL> ";"                  { return nextToken(sym.SEMICOLON); }
<YYINITIAL> "."                  { return nextToken(sym.DOT); }
<YYINITIAL> "("                  { return nextToken(sym.LPAREN); }
<YYINITIAL> ")"                  { return nextToken(sym.RPAREN); }
 


 
<STRING>   \'                             { yybegin(YYINITIAL); 
                                 return nextToken(sym.STRING,string.toString());}
<STRING>  [^\n\r\'\\]+                   { string.append( yytext() ); }
<STRING>  \\t                            { string.append('\t'); }
<STRING>  \\n                            { string.append('\n'); }

<STRING>  \\r                            { string.append('\r'); }
<STRING>  \\\"                           { string.append('\"'); }
<STRING>  \\                             { string.append('\\'); }
 
