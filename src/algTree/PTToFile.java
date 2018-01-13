package algTree;

import java.io.FileWriter;
import java.io.IOException;

import algTree.PatriciaTree.Node;

public class PTToFile {

	public PTToFile(PatriciaTree tree) {
		try {
			// System.out.println(leaf.m_Key);
			// deepestLeaf(tree.getRoot(), 0);
			// System.out.println(leaf.m_Key);
			root = tree.getRoot();
			startWriting(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void startWriting(Node root) throws IOException {
		FileWriter f = new FileWriter("tree.txt");
		f.write("[");
		printNode(f, root);
		writeChildren(f, root);
		f.write("))");
		f.write("]");
		f.close();
	}

	private void writeChild(FileWriter f, Node from, Node to, String s, boolean b) throws IOException {
		printEdge(f, from, to, b);
		printNode(f, to);
		f.write(s);
		writeChildren(f, to);
	}

	private void writeChildren(FileWriter f, Node node) throws IOException {
		boolean b = false;
		if (node.m_Left != null || node.m_Right != null) {
			if (node.m_Left != null) {
				if (node.m_Right != null) {
					if (node.m_BitPos >= node.m_Left.m_BitPos) {
						printRefNode(f, node, node.m_Left, "))", true);
						b = true;
					} else {
						writeChild(f, node, node.m_Left, "", true);
					}
					if (node.m_BitPos >= node.m_Right.m_BitPos) {
						printRefNode(f, node, node.m_Right, "))]", false);
						b = true;
					} else {
						writeChild(f, node, node.m_Right, "", false);
					}
					if (!b) {
						f.write("))))]");
					}
				} else {
					if (node.m_BitPos >= node.m_Left.m_BitPos) {
						printRefNode(f, node, node.m_Left, "))]))))", true);
						b = true;
					} else {
						writeChild(f, node, node.m_Left, "", true);
					}
					if (!b) {
						f.write("))]");
					}
				}
			} else {
				if (node.m_Right != null) {
					if (node.m_BitPos >= node.m_Right.m_BitPos) {
						printRefNode(f, node, node.m_Right, "))]))))", true);
						b = true;
					} else {
						writeChild(f, node, node.m_Right, "", true);
					}
					if (!b) {
						f.write("))]");
					}
				}
			}
		}

	}

	private void printNode(FileWriter f, Node node) {
		try {
			if (node.equals(root)) {
				f.write("\nl(\"" + node.m_Key + "\",n(\"\",[a(\"OBJECT\",\"" + node.m_Key + "\")]");
			} else {
				f.write(",\nl(\"" + node.m_Key + "\",n(\"\",[a(\"OBJECT\",\"" + node.m_Key + "\")]");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void printEdge(FileWriter f, Node from, Node to, boolean b) {
		try {
			if (b) {
				f.write(",\n[\nl(\"" + from.m_Key + to.m_Key + "\",e(\"\",[a(\"EDGECOLOR\",\"black\")]");
			} else {
				f.write(",\nl(\"" + from.m_Key + to.m_Key + "\",e(\"\",[a(\"EDGECOLOR\",\"black\")]");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void printRefNode(FileWriter f, Node from, Node to, String s, boolean b) {
		try {
			printEdge(f, from, to, b);
			f.write(",\nr(\"" + to.m_Key + "\")" + s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	private void deepestLeaf(Node node, int lvl) {
//		if ((node.m_Left.m_BitPos <= node.m_BitPos) || (node.m_Right.m_BitPos <= node.m_BitPos) && lvl >= level) {
//			leaf = node;
//			level = lvl;
//		}
//		if ((node.m_Right != null) && (node.m_Right.m_BitPos > node.m_BitPos)) {
//			deepestLeaf(node.m_Right, lvl + 1);
//		}
//		if ((node.m_Left != null) && (node.m_Left.m_BitPos > node.m_BitPos)) {
//			deepestLeaf(node.m_Left, lvl + 1);
//		}
//	}

	private Node root;
//	private Node leaf;
//	private int level;

}
