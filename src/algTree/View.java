package algTree;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import algTree.Model.TreeType;

public class View extends JFrame {
	public View(Model m) {
		m_Mod = m;
		

		setLayout(new BorderLayout());

		m_NodeList = new DefaultListModel<String>();
		JList list = new JList<String>(m_NodeList);

		list.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel edit = new JPanel();
		JPanel textArea = new JPanel();

		JTextField keyField = new JTextField();
		JTextField valueField = new JTextField();

		textArea.setLayout(new GridLayout(2, 2));
		textArea.add(new JLabel("Key: "));
		textArea.add(keyField);

		if (m_Mod.getTreeType().equals(TreeType.RBTree)) {
			textArea.add(new JLabel("Value: "));
			textArea.add(valueField);
		}

		edit.setLayout(new FlowLayout());
		edit.add(textArea);

		JButton insertButton = new JButton("insert");
		insertButton.setEnabled(false);
		JButton removeButton = new JButton("remove");
		removeButton.setEnabled(false);
		insertButton.addActionListener(e -> {
			if (m_Mod.getTreeType().equals(TreeType.RBTree)) {
				if (!keyField.getText().equals("") && !valueField.getText().equals("")) {
					m_Mod.insert(keyField.getText(), valueField.getText());
					insertButton.setEnabled(false);
					if (!m_NodeList.contains(keyField.getText())) {
						m_NodeList.addElement(keyField.getText());
					}
					keyField.setText("");
					valueField.setText("");
				}
			}else {
				if (!keyField.getText().equals("")) {
					m_Mod.insert(keyField.getText(),null);
					insertButton.setEnabled(false);
					if (!m_NodeList.contains(keyField.getText())) {
						m_NodeList.addElement(keyField.getText());
					}
					keyField.setText("");
					valueField.setText("");
				}
			}
		});
		removeButton.addActionListener(e -> {
			m_Mod.remove((String) list.getSelectedValue());
			m_NodeList.removeElement(list.getSelectedValue());
			removeButton.setEnabled(false);
		});
		JButton writeButton = new JButton("write");
		writeButton.addActionListener(e -> {
			writeFile();
		});

		keyField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (m_Mod.getTreeType().equals(TreeType.RBTree)) {
					if (keyField.getText().equals("") || valueField.getText().equals("")) {
						insertButton.setEnabled(false);
					}
				} else {
					if (keyField.getText().equals("")) {
						insertButton.setEnabled(false);
					}
				}

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (m_Mod.getTreeType().equals(TreeType.RBTree)) {
					if (!valueField.getText().equals("")) {
						insertButton.setEnabled(true);
					}
				} else {
					insertButton.setEnabled(true);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if (m_Mod.getTreeType().equals(TreeType.RBTree)) {
					if (keyField.getText().equals("") || valueField.getText().equals("")) {
						insertButton.setEnabled(false);
					}
					if (!valueField.getText().equals("")) {
						insertButton.setEnabled(true);
					}
				} else {
					if (!keyField.getText().equals("")) {
						insertButton.setEnabled(true);
					}else {
						insertButton.setEnabled(false);
					}
				}
			}
		});

		valueField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (keyField.getText().equals("") || valueField.getText().equals("")) {
					insertButton.setEnabled(false);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (!keyField.getText().equals("")) {
					insertButton.setEnabled(true);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if (!keyField.getText().equals("")) {
					insertButton.setEnabled(true);
				}
			}
		});

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				removeButton.setEnabled(true);
			}
		});

		edit.add(insertButton);
		edit.add(removeButton);
		edit.add(writeButton);

		add(list, BorderLayout.CENTER);
		add(edit, BorderLayout.SOUTH);

		setSize(400, 300);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void writeFile() {
		if (m_Mod.getTreeType().equals(TreeType.RBTree)) {
			TreeToFile ttf = new TreeToFile(m_Mod.getM_RBT());
		}else if (m_Mod.getTreeType().equals(TreeType.PatriciaTree)) {
			PTToFile ttf = new PTToFile(m_Mod.getM_PT());
		}
		pBuilder = new ProcessBuilder("C:\\Program Files (x86)\\uDraw(Graph)\\bin\\uDrawGraph.exe", "tree.txt");
		try {
			pBuilder.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Model m_Mod;
	private DefaultListModel<String> m_NodeList;
	private ProcessBuilder pBuilder;
}
