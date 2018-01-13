package algTree;

import javax.swing.JOptionPane;

import algTree.Model.TreeType;

public class Controller {
	Model m = new Model();
	View v;
	public Controller() {
		Object[] options = {TreeType.RBTree, TreeType.PatriciaTree, TreeType.RoBDD};

		int selected = JOptionPane.showOptionDialog(null, "Choose a tree:", "Trees", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		m.setTreeType((TreeType) options[selected]);
		if (m.getTreeType().equals(TreeType.RBTree) || m.getTreeType().equals(TreeType.PatriciaTree)) {
			View v = new View(m);
		}else {
			new RoView(m);
		}
	}
}
