package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame{
	private ControlPanel cntrlPanel;
	private BodiesTable bTable;
	private Viewer viewer;
	private StatusBar statBar;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8877948025931264431L;
	
	Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		this.initGUI();
	}

	private void initGUI() {
		// TODO complete this method to build the GUI
		this.setLocation(550, 0);
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setMinimumSize(new Dimension(750, 1000));
		this.setPreferredSize(new Dimension(750, 1000));
		setContentPane(mainPanel);
		this.cntrlPanel = new ControlPanel(this._ctrl, this);
		this.bTable = new BodiesTable(this._ctrl);
		this.bTable.setPreferredSize(new Dimension(750, 350));
		this.viewer = new Viewer(this._ctrl);
		this.viewer.setVisible(true);
		this.viewer.setPreferredSize(new Dimension(7500, 650));
		this.statBar = new StatusBar(this._ctrl);
		mainPanel.add(this.cntrlPanel, BorderLayout.PAGE_START);
		mainPanel.add(this.statBar, BorderLayout.PAGE_END);
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		mainPanel.add(center, BorderLayout.CENTER);
		center.add(bTable);
		center.add(viewer);
		this.setVisible(true);
	}

}
