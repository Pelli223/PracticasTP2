package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import simulator.model.BodiesTableModel;

public class BodiesTable extends JPanel{

	private JTable bodies;
	private BodiesTableModel model;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	BodiesTable(Controller ctrl) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(
		BorderFactory.createLineBorder(Color.black, 2),
		"Bodies",
		TitledBorder.LEFT, TitledBorder.TOP));
		// TODO complete
		model = new BodiesTableModel(ctrl);
		bodies = new JTable(model);
		bodies.setShowGrid(false);
		JScrollPane scroll = new JScrollPane(bodies,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scroll);
	}
}
