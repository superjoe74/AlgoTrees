package algTree;

public class Model {

	public enum TreeType {
		RBTree, PatriciaTree, RoBDD
	}

	public void createTree() {
		if (m_TreeType == TreeType.PatriciaTree) {
			m_PT = new PatriciaTree();
		} else if (m_TreeType == TreeType.RBTree) {
			m_RBT = new RBTree();
		} else {
			
		}
	}

	public void insert(String key, String value) {
		if (m_TreeType == TreeType.RBTree) {
			if (m_RBT == null) {
				m_RBT = new RBTree<String>();
			}
			m_RBT.insert(key, value);
		}else if (m_TreeType == TreeType.PatriciaTree) {
			if (m_PT == null) {
				m_PT = new PatriciaTree();
			}
			m_PT.insert(key);
		}else {
			BoolExpr expr = new BoolExpr(key);
		}
	}

	public void remove(String selectedValue) {
		if (m_TreeType.equals(TreeType.RBTree)) {
			m_RBT.remove(selectedValue);
		}else if (m_TreeType.equals(TreeType.PatriciaTree)) {
			m_PT.remove(selectedValue);
		}
	}

	public void setTreeType(TreeType treeType) {
		m_TreeType = treeType;
	}

	public TreeType getTreeType() {
		return m_TreeType;
	}

	public RBTree getM_RBT() {
		return m_RBT;
	}

	public PatriciaTree getM_PT() {
		return m_PT;
	}

	private TreeType m_TreeType;
	private RBTree m_RBT;
	private PatriciaTree m_PT;
	private RoBDD m_Ro;

}
