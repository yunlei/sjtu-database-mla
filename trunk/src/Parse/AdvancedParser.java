package Parse;

import java_cup.runtime.Scanner;
import java_cup.runtime.SymbolFactory;
import ErrorMsg.ErrorMsg;

public class AdvancedParser extends parser{

	public AdvancedParser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdvancedParser(Lexer l, ErrorMsg err) {
		super(l, err);
		// TODO Auto-generated constructor stub
	}

	public AdvancedParser(Scanner s, SymbolFactory sf) {
		super(s, sf);
		// TODO Auto-generated constructor stub
	}

	public AdvancedParser(Scanner s) {
		super(s);
		// TODO Auto-generated constructor stub
	}
	
}
