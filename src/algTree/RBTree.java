package algTree;

public class RBTree<D> {
	class Node{		
		public Node(String key, D data) {
			m_Key = key;
			m_Data = data;
		}
		String m_Key;
		D m_Data;
		Node m_Left = null;
		Node m_Right = null;
		boolean m_bIsRed = true;
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
	
	private Node m_Root = null;
}