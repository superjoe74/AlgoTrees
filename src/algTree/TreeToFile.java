package algTree;

import java.io.FileWriter;
import java.io.IOException;

import algTree.PatriciaTree.Node;


public class TreeToFile {
	private int level;
	private Node leaf;
	public TreeToFile(PatriciaTree tree) {
		try {
			deepestLeaf(tree.getRoot(), 0);
			System.out.println(leaf.m_Key);
			startWriting(tree.getRoot());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void startWriting(Node root) throws IOException {
		FileWriter f = new FileWriter("tree.txt");
		f.write("[");
		printNode(f, root);
		if (root.m_Left != null || root.m_Right != null) {
			f.write("\n[");
			if (root.m_Left != null) {
				if (root.m_Right != null) {
					writeChild(f, root, root.m_Left, "[])))),");
					if (root.m_Right == leaf) {
						writeChild(f, root, root.m_Right, "[]");
					}else {
						writeChild(f, root, root.m_Right, "");
					}
					f.write("))))]");
				} else {
					if (root.m_Left == leaf) {
						writeChild(f, root, root.m_Left, "))");
					}else {
						writeChild(f, root, root.m_Left, "[]))");
					}
					f.write("))]");
				}
			} else {
				if (root.m_Right != null) {
					if (root.m_Right == leaf) {
						writeChild(f, root, root.m_Right, "[]))");
					}else {
						writeChild(f, root, root.m_Right, "))");
					}
					f.write("))]");
				}
			}
		}
		f.write("))");
		f.write("]");
		f.close();
	}

	private void writeChild(FileWriter f, Node from, Node to, String s) throws IOException {
		printEdge(f, from, to);
		printNode(f, to);
		f.write(s);
		if (to.m_Left != null || to.m_Right != null) {
			f.write("\n[");
			if (to.m_Left != null) {
				if (to.m_Right != null) {
					writeChild(f, to, to.m_Left, "[])))),");
					if (to.m_Right == leaf) {
						writeChild(f, to, to.m_Right, "[]");
					}else {
						writeChild(f, to, to.m_Right, "");
					}
					f.write("))))]");
				} else {
					if (to.m_Left == leaf) {
						writeChild(f, to, to.m_Left, "))");
					}else {
						writeChild(f, to, to.m_Left, "[]))");
					}
					f.write("))]");
				}
			} else {
				if (to.m_Right != null) {
					if (to.m_Right == leaf) {
						writeChild(f, to, to.m_Right, "[]))");
					}else {
						writeChild(f, to, to.m_Right, "))");
					}
					f.write("))]");
				}
			}
		}
	}

	

	private void printNode(FileWriter f, Node node) {
		try {
			f.write("\nl(\"" + node.m_Key + "\",n(\"\",[a(\"OBJECT\",\"" + node.m_Key + "\")],");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void printEdge(FileWriter f, Node from, Node to) {
		try {
//			String col = "black";
//			if (to.m_bIsRed) {
//				col = "red";
//			}
			f.write("\nl(\"" + from.m_Key + to.m_Key + "\",e(\"\",[a(\"EDGECOLOR\",\""+"black"+"\")],");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void deepestLeaf(Node node, int lvl) {
		if (node.m_Left == null && node.m_Right == null
                && lvl >= level) {
			leaf = node;
			level = lvl;
		}
		if (node.m_Left != null) {
			deepestLeaf(node.m_Left, lvl+1);
		}
		if (node.m_Right != null) {
			deepestLeaf(node.m_Right, lvl+1);
		}
	}
}
