 package java_cup.runtime;
 
 /**
  * Default Implementation for SymbolFactory, creates
  * plain old Symbols
  *
  * @version last updated 27-03-2006
  * @author Michael Petter
  */
 
 /* *************************************************
   class DefaultSymbolFactory
 
   interface for creating new symbols 
  ***************************************************/
 public class DefaultSymbolFactory implements SymbolFactory{
     // Factory methods
 /**
      * DefaultSymbolFactory for CUP.
      *       * Users are strongly encoraged to use ComplexSymbolFactory instead, since
21      * it offers more detailed information about Symbols in source code.
22      * Yet since migrating has always been a critical process, You have the
23      * chance of still using the oldstyle Symbols.
24      *
25      * @deprecated as of CUP v11a
26      * replaced by the new java_cup.runtime.ComplexSymbolFactory
27      */
    //@deprecated 
 public DefaultSymbolFactory(){
     }
    public Symbol newSymbol(String name ,int id, Symbol left, Symbol right, Object value){
       return new Symbol(id,left,right,value);
    }
    public Symbol newSymbol(String name, int id, Symbol left, Symbol right){
        return new Symbol(id,left,right);
     }
    public Symbol newSymbol(String name, int id, int left, int right, Object value){
         return new Symbol(id,left,right,value);
    }
     public Symbol newSymbol(String name, int id, int left, int right){
        return new Symbol(id,left,right);
     }
   public Symbol startSymbol(String name, int id, int state){
        return new Symbol(id,state);
    }
   public Symbol newSymbol(String name, int id){
        return new Symbol(id);
     }
    public Symbol newSymbol(String name, int id, Object value){
         return new Symbol(id,value);
    }
 }

 