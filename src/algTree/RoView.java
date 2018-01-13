package algTree;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import algTree.Model.TreeType;

public class RoView extends JFrame {
	public RoView(Model m) {
		setLayout(new FlowLayout());

		JTextField boolExpr = new JTextField();
boolExpr.setPreferredSize(new Dimension(300, 100));
		JButton create = new JButton("create & write");
		create.addActionListener(e -> {
			m.insert(boolExpr.getText(), null);
		});

		boolExpr.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				if (m_Mod.getTreeType().equals(TreeType.RBTree)) {
					if (boolExpr.getText().equals("")) {
						create.setEnabled(false);
					}
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				create.setEnabled(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});
		add(boolExpr);
		add(create);

		setSize(400, 300);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private Model m_Mod;
}
