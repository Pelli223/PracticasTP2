package simulator.view;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import simulator.model.ParametersTableModel;

public class ParametersTable extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable params;

	public ParametersTable(ParametersTableModel model) {
		setLayout(new BorderLayout());
		params = new JTable(model);
		params.setShowGrid(false);
		JScrollPane scroll = new JScrollPane(params,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scroll);
	}
}
