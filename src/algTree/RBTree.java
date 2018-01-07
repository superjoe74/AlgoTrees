package algTree;

public class RBTree<D> {
	class Node {
		public Node(String key, D data) {
			m_Key = key;
			m_Data = data;
		}

		public boolean is4Node() {
			return m_Left != null && m_Left.m_bIsRed && m_Right != null && m_Right.m_bIsRed;
		}

		public boolean is2Node() {
			return !m_bIsRed && (m_Left == null || !m_Left.m_bIsRed) && (m_Right == null || !m_Right.m_bIsRed);
		}

		public void convert4Node() {
			m_Left.m_bIsRed = false;
			m_Right.m_bIsRed = false;
			m_bIsRed = true;
		}

		

		String m_Key;
		D m_Data;
		Node m_Left = null;
		Node m_Right = null;
		boolean m_bIsRed = true;

	}

	class NodeHandler {

		public final int NODE = 0;
		public final int DAD = 1;
		public final int G_DAD = 2;
		public final int GG_DAD = 3;
		private Object[] m_Nodes = new Object[4];

		NodeHandler(Node n) {
			m_Nodes[NODE] = n;
		}

		NodeHandler(NodeHandler h) {
			m_Nodes[NODE] = h.m_Nodes[NODE];
			m_Nodes[DAD] = h.m_Nodes[DAD];
			m_Nodes[G_DAD] = h.m_Nodes[G_DAD];
			m_Nodes[GG_DAD] = h.m_Nodes[GG_DAD];
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

		void set(Node n, int kind, boolean copyColours) {
			if (node(kind + 1) == null)
				m_Root = n;
			else if (node(kind) != null ? node(kind + 1).m_Left == node(kind)
					: n.m_Key.compareTo(node(kind + 1).m_Key) < 0)
				node(kind + 1).m_Left = n;   
			else
				node(kind + 1).m_Right = n;
			if (copyColours && node(kind) != null && n != null)
				n.m_bIsRed = node(kind).m_bIsRed;
			m_Nodes[kind] = n;
		}

		void rotate(int kind) {
			Node dad = node(kind);
			Node son = node(kind - 1);
			boolean sonColour = son.m_bIsRed;
			if (!sonColour) {
				if (son.m_Left != null)
					son.m_Left.m_bIsRed = false;
				if (son.m_Right != null)
					son.m_Right.m_bIsRed = false;
				dad.m_bIsRed = false;
				dad.m_Left.m_bIsRed = true;
				dad.m_Right.m_bIsRed = true;
			} else {
				son.m_bIsRed = dad.m_bIsRed;
				dad.m_bIsRed = sonColour;
			}
			// rotate
			if (dad.m_Left == son) {
				// clockwise rotation
				dad.m_Left = son.m_Right;
				son.m_Right = dad;
			} else {
				// counter-clockwise rotation
				dad.m_Right = son.m_Left;
				son.m_Left = dad;
			}
			set(son, kind, false);
		}

		public void split() {
			Node dad = node(DAD);
			if (dad != null && dad.m_bIsRed) {
				if (node(G_DAD).m_Key.compareTo(dad.m_Key) < 0 != dad.m_Key.compareTo(node(NODE).m_Key) < 0)
					rotate(DAD);
				rotate(G_DAD);
			}
		}

		private void join() {
			if (node(NODE).is2Node()) {
				if (node(DAD) == null && node(NODE).m_Left != null && node(NODE).m_Left.is2Node()
						&& node(NODE).m_Right != null && node(NODE).m_Right.is2Node()) {
					node(NODE).m_Left.m_bIsRed = true;
					node(NODE).m_Right.m_bIsRed = true;
				} else if (node(DAD) != null) {
					NodeHandler nephew = getNephew();
					if (nephew.node(DAD).m_bIsRed) {
						nephew.rotate(G_DAD);
						m_Nodes[GG_DAD] = m_Nodes[G_DAD];
						m_Nodes[G_DAD] = nephew.m_Nodes[G_DAD];
						nephew = getNephew();
					}
					if (nephew.node(DAD).is2Node()) {
						node(NODE).m_bIsRed = true;
						nephew.node(DAD).m_bIsRed = true;
						node(DAD).m_bIsRed = false;
					} else {
						if (!nephew.isNull() && nephew.node(NODE).m_bIsRed)
							nephew.rotate(DAD);
						nephew.rotate(G_DAD);
					}
				}
			}
		}

		NodeHandler getNephew() {
			Node node = node(NODE);
			Node dad = node(DAD);
			Node gDad = node(G_DAD);
			Node brother = node == dad.m_Left ? dad.m_Right : dad.m_Left;
			Node nephew = node == dad.m_Left ? brother.m_Left : brother.m_Right;
			NodeHandler res = new NodeHandler(nephew);
			res.m_Nodes[DAD] = brother;
			res.m_Nodes[G_DAD] = dad;
			res.m_Nodes[GG_DAD] = gDad;
			return res;
		}
	}

	public Node search(String key) {
		Node tmp = m_Root;
		while (tmp != null) {
			int dist = key.compareTo(tmp.m_Key);
			if (dist == 0)
				return tmp;
			tmp = dist < 0 ? tmp.m_Left : tmp.m_Right;
		}
		return null;
	}

	boolean insert(String key, D data) {
		NodeHandler h = new NodeHandler(m_Root);
		while (!h.isNull()) {
			if (h.node(h.NODE).is4Node()) {
				h.node(h.NODE).convert4Node();
			}
			int dist = key.compareTo(h.node(h.NODE).m_Key);
			if (dist == 0)
				return false;
			h.down(dist < 0);
		}
		h.set(new Node(key, data), h.NODE, false);
		h.split();
		m_Root.m_bIsRed = false;
		return true;
	}

	boolean remove(String key) {
		NodeHandler h = new NodeHandler(m_Root);
		while (!h.isNull()) {
			h.join();
			final int RES = key.compareTo(h.node(h.NODE).m_Key);
			if (RES == 0) {
				if (h.node(h.NODE).m_Right == null) {
					h.set(h.node(h.NODE).m_Left, h.NODE, true);
				} else {
					NodeHandler h2 = new NodeHandler(h);
					h2.down(false); // go right
					h2.join();
					while (h2.node(h2.NODE).m_Left != null) {
						h2.down(true);
						h2.join();
					}
					h.node(h.NODE).m_Key = h2.node(h2.NODE).m_Key;
					h.node(h.NODE).m_Data = h2.node(h2.NODE).m_Data;
					h2.set(h2.node(h2.NODE).m_Right, h2.NODE, true);
				}
				if (m_Root != null)
					m_Root.m_bIsRed = false;
				return true;
			}
			h.down(RES < 0);
		}
		return false;
	}

	private Node m_Root = null;

	public Node getRoot() {
		return m_Root;
	}
}