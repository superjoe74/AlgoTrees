package algTree;

import BoolExprParser.*;

public class BoolExpr {
	static RoBDD tree = new RoBDD();
	public static void print(Node n) {
		if (n != null) {
			if (n.type() != Node.Type.VAR)
				System.out.print("(");
			switch (n.type()) {
				case VAR:  ;break;
				case NOT:
					System.out.print("!");
					print(n.left());
					break;
				case AND:case OR:case IMPLIES:case EQUIV:
					print(n.left());
					switch(n.type()) {
						case AND:System.out.print("&");break;
						case OR:System.out.print("|");break;
						case IMPLIES:System.out.print("=>");break;
						case EQUIV:System.out.print("<=>");break;
					}
					print(n.right());
					break;
			}
			if (n.type() != Node.Type.VAR)
				System.out.print(")");
		}
	}

	public static void parseAndPrint(String input) {
		try {
			BoolExprParser p = new BoolExprParser(new BoolExprScanner(new java.io.StringReader(input)));
			java_cup.runtime.Symbol s = p.parse();
			print((Node)s.value);
			System.out.println();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} catch (Error e) {
			System.out.println(e.getMessage());
		}
	}
}
