package algTree;

import BoolExprParser.*;
import algTree.RoBDD.Func;

public class BoolExpr {
	
	RoBDD tree;
	
	public BoolExpr(String s) {
		tree = new RoBDD();
		parseAndPrint(s);
		tree.print(tree.m_lastFunc);
	}
	
	public Func print(Node n) {
		if (n != null) {
			switch (n.type()) {
				case VAR: return tree.genVar(n.name());
				case NOT: return tree.ite(print(n.left()), tree.genFalse(), tree.genTrue());
				case AND: return tree.ite(print(n.left()), print(n.right()), tree.genFalse());
				case OR: return tree.ite(print(n.left()), tree.genTrue(), print(n.right()));
				case IMPLIES: return tree.ite(print(n.left()), print(n.right()), tree.genTrue());
				case EQUIV: return tree.ite(print(n.left()), print(n.right()), tree.ite(print(n.right()), tree.genFalse(), tree.genTrue()));
			}
		}
		return null;
	}

	public void parseAndPrint(String input) {
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
