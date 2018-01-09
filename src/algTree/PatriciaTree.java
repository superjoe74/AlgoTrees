package algTree;

public class PatriciaTree {

	static boolean left(String key, int bitPos) {
		if (key == null) {
			return true;
		}
		int index = bitPos / Character.SIZE;
		int bit = bitPos % Character.SIZE;

		if (index >= key.length()) {
			return true;
		}
		
		return (key.charAt(index) &  (1 << bit)) == 0;
	}

	class Node {
		public Node(String key, int bitPos, Node succ) {
			m_Key = key;
			m_BitPos = bitPos;
			boolean bIsLeft = left(key, bitPos);
			m_Left = bIsLeft ? this : succ;
			m_Right = bIsLeft ? succ : this;
		}

		public Node(String key, int bitPos) {
			this(key, bitPos, null);
		}

		public String m_Key;
		public int m_BitPos;
		public Node m_Left;
		public Node m_Right;
	}

	public boolean search(String c) {
		NodeHandler h = new NodeHandler(m_Root);
		h.search(c);
		return !h.isNull() && h.node(h.NODE).m_Key.equals(c);
	}

	public boolean insert(String c) {
		NodeHandler h = new NodeHandler(m_Root);
		h.search(c);
		int index = 0;
		if (h.isNull()) {
			if (h.node(h.DAD) != null) {
				while (left(c, index) == left(h.node(h.DAD).m_Key, index) && index < h.node(h.DAD).m_BitPos)
					++index;
				if (index == h.node(h.DAD).m_BitPos)
					++index;
			}
		} else if (!h.node(h.NODE).m_Key.equals(c)) {
			while (left(c, index) == left(h.node(h.NODE).m_Key, index))
				++index;
		} else {
			// already inserted
			return false;
		}
		h = new NodeHandler(m_Root);
		h.search(c, index);
		h.set(new Node(c, index, h.node(h.NODE)), h.NODE);
		return true;
	}

	boolean remove(String c) {
		NodeHandler h = new NodeHandler(m_Root);
		h.search(c);
		if (!(h.isNull() || h.node(h.NODE).m_Key.equals(c))) {
			return false;
		} else {
			NodeHandler h2 = new NodeHandler(h.node(h.DAD));
			h2.search(h.node(h.DAD).m_Key);
			h.node(h.NODE).m_Key = h.node(h.DAD).m_Key;
			h2.set(h.node(h.NODE), h2.NODE);
			h.set(h.brother(h.NODE), h.DAD);
		}
		return true;
	}

	private Node m_Root;

	class NodeHandler {
		public final int NODE = 0;
		public final int DAD = 1;
		private Object[] m_Nodes = new Object[3];

		NodeHandler(Node n) {
			m_Nodes[NODE] = n;
		}

		public Node brother(int kind) {
			Node dad = node(kind + 1);
			Node node = node(kind);
			return dad.m_Left == node ? dad.m_Right : dad.m_Left;
		}

		void down(boolean left) {
			for (int i = m_Nodes.length - 1; i > 0; --i)
				m_Nodes[i] = m_Nodes[i - 1];
			m_Nodes[NODE] = left ? node(DAD).m_Left : node(DAD).m_Right;
		}

		boolean isNull() {
			return m_Nodes[NODE] == null;
		}

		Node node(int kind) {
			return (Node) m_Nodes[kind];
		}

		void set(Node n, int kind) {
			if (node(kind + 1) == null)
				m_Root = n;
			else if (node(kind) != null ? node(kind + 1).m_Left == node(kind) : left(n.m_Key, node(kind + 1).m_BitPos))
				node(kind + 1).m_Left = n;
			else
				node(kind + 1).m_Right = n;
			m_Nodes[kind] = n;
		}

		void search(String c, int maxPos) {
			int lastBitPos = -1;
			while (!isNull() && lastBitPos < node(NODE).m_BitPos && maxPos > node(NODE).m_BitPos) {
				lastBitPos = node(NODE).m_BitPos;
				down(left(c, lastBitPos));
			}
		}

		void search(String c) {
			search(c, Integer.MAX_VALUE);
		}
	}

	public Node getRoot() {
		return m_Root;
	}
}
