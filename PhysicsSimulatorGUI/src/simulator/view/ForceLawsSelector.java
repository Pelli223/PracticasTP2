package simulator.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.JSONObject;

import simulator.model.ParametersTableModel;

public class ForceLawsSelector extends JDialog{

	private ParametersTableModel model;
	private int _status;
	private List<JSONObject> data;
	private JComboBox<String> laws;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ForceLawsSelector(JFrame parent, List<JSONObject> data) {
		super(parent, true);
		this.data = data;
		initGUI();
	}

	private void initGUI() {
		// TODO Auto-generated method stub
		_status = 0;
		setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		JLabel help = new JLabel("<html><p>Select force law and provide values for the parameters in the Value column (default values are used <br>for parameters with no value</p></html>");
		help.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(help);
		JLabel box = new JLabel("Force laws: ");
		laws = new JComboBox<String>();
		for(int i = 0; i < data.size(); i++) {
			laws.addItem(data.get(i).getString("desc"));
		}
		model = new ParametersTableModel(this.data.get(laws.getSelectedIndex()).getJSONObject("data"));
		laws.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				model.setParameters(data.get(laws.getSelectedIndex()).getJSONObject("data"));
			}
		});
		laws.setMinimumSize(new Dimension(475, 20));
		laws.setMaximumSize(new Dimension(475, 20));
		laws.setPreferredSize(new Dimension(475, 20));
		JTable dataTable = new JTable(model) {
			private static final long serialVersionUID = 1L;

			// we override prepareRenderer to resized rows to fit to content
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}
		};
		dataTable.setShowGrid(false);
		JScrollPane tabelScroll = new JScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(tabelScroll);
		
		JPanel forceLaws = new JPanel(new FlowLayout());
		forceLaws.add(box);
		forceLaws.add(laws);
		mainPanel.add(forceLaws);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// bottons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);

		mainPanel.add(buttonsPanel);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				ForceLawsSelector.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 1;
				ForceLawsSelector.this.setVisible(false);
			}
		});
		buttonsPanel.add(okButton);

		setPreferredSize(new Dimension(600, 400));
		setMinimumSize(new Dimension(600, 400));
		setMaximumSize(new Dimension(600, 400));
		pack();
		setResizable(false); // change to 'true' if you want to allow resizing
		setVisible(false); // we will show it only whe open is called
	}

	public int open() {

		if (getParent() != null)
			setLocation(//
					getParent().getLocation().x + getParent().getWidth() / 2 - getWidth() / 2, //
					getParent().getLocation().y + getParent().getHeight() / 2 - getHeight() / 2);
		pack();
		setVisible(true);
		return _status;
	}

	public JSONObject getJSON() {
		JSONObject info = new JSONObject();
		JSONObject dataModel = new JSONObject(model.getData());
		info.put("type", data.get(laws.getSelectedIndex()).getString("type"));
		info.put("data", dataModel);
		return info;
	}
}
