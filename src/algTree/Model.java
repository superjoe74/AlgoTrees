package algTree;

public class Model {
	
	public enum TreeType{
		RBTree, PatriciaTree, RoBDD
	}
	
	public void createTree() {
		if (m_TreeType == TreeType.PatriciaTree) {
			m_PT = new PatriciaTree();
		} else if (m_TreeType == TreeType.RBTree) {
			m_RBT = new RBTree();
		} else {
			m_Ro = new RoBDD();
		}
	}
	
	public void insert() {
		
	}

	public void setTreeType(TreeType treeType) {
		m_TreeType = treeType;
	}
	
	private TreeType m_TreeType;
	private RBTree m_RBT;
	private PatriciaTree m_PT;
	private RoBDD m_Ro;
}
