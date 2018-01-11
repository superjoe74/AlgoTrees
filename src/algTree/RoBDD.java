package algTree;

import java.util.Hashtable;

public class RoBDD {
	private static final class Func {
		private static final int TRUE = 0x7fffffff;
		private static final int FALSE = TRUE - 1;
		private final int m_ciVar;
		private final Func m_cThen, m_cElse;

		Func(boolean b) {
			m_ciVar = b ? TRUE : FALSE;
			m_cThen = m_cElse = null;
		}

		Func(int iVar, Func t, Func e) {
			m_ciVar = iVar;
			m_cThen = t;
			m_cElse = e;
		}

		Func getThen(int iVar) {
			return iVar == m_ciVar ? m_cThen : this;
		}

		Func getElse(int iVar) {
			return iVar == m_ciVar ? m_cElse : this;
		}

		int getVar() {
			return m_ciVar;
		}

		boolean isTrue() {
			return m_ciVar == TRUE;
		}

		boolean isFalse() {
			return m_ciVar == FALSE;
		}

		boolean isConstant() {
			return isTrue() || isFalse();
		}
	}

	private static final class Triple {
		private final int m_ciVar;
		private final Func m_cThen;
		private final Func m_cElse;

		Triple(int iVar, Func fThen, Func fElse) {
			m_ciVar = iVar;
			m_cThen = fThen;
			m_cElse = fElse;
		}

		public boolean equals(Object obj) {
			if (obj instanceof Triple) {
				Triple arg = (Triple) obj;
				return arg.m_ciVar == m_ciVar && arg.m_cThen == m_cThen && arg.m_cElse == m_cElse;
			}
			return false;
		}

		public int hashCode() {
			return m_ciVar ^ m_cThen.hashCode() ^ m_cElse.hashCode();
		}
	}

	private final Func m_cTrue;
	private final Func m_cFalse;
	private Hashtable<String, Integer> m_Integers;
	private Hashtable<Integer, Triple> m_Triples;
	private Hashtable<Triple, Func> m_Unique;
	private int m_Entries;

	RoBDD() {
		m_cTrue = new Func(true);
		m_cFalse = new Func(false);
		m_Integers = new Hashtable<String, Integer>();
		m_Triples = new Hashtable<Integer, Triple>();
		m_Unique = new Hashtable<Triple, Func>();
	}

	Func genVar(String s) {
		Integer i = m_Integers.get(s);
		if (i == null) {
			i = ++m_Entries;
			m_Integers.put(s, i);
		}
		Triple entry = m_Triples.get(i);
		if (entry == null) {
			entry = new Triple(i, genTrue(), genFalse());
			m_Triples.put(i, entry);
		}
		Func res = m_Unique.get(entry);
		if (res == null) {
			res = new Func(i, genTrue(), genFalse());
			m_Unique.put(entry, res);
		}
		return res;
	}

	Func genTrue() {
		return m_cTrue;
	}

	Func genFalse() {
		return m_cFalse;
	}

	Func ite(Func i, Func t, Func e) {
		if (i.isTrue())
			return t;
		else if (i.isFalse())
			return e;
		else if (t.isTrue() && e.isFalse())
			return i;
		else {
			final int ciVar = Math.min(Math.min(i.getVar(), t.getVar()), e.getVar());
			final Func T = ite(i.getThen(ciVar), t.getThen(ciVar), e.getThen(ciVar));
			final Func E = ite(i.getElse(ciVar), t.getElse(ciVar), e.getElse(ciVar));
			if (T.equals(E))
				return T;
			final Triple entry = new Triple(ciVar, T, E);
			Func res = m_Unique.get(entry);
			if (res == null) {
				res = new Func(ciVar, T, E);
				m_Unique.put(entry, res);
			}
			return res;
		}
	}
}
