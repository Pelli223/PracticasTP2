package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2968013173547630455L;
	
	private JLabel _currTime; // for current time
	private JLabel _currLaws; // for gravity laws
	private JLabel _numOfBodies; // for number of bodies
	
	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout( new FlowLayout( FlowLayout.LEFT ));
		this.setBorder( BorderFactory.createBevelBorder( 1 ));
		// TODO complete the code to build the tool bar
		this._currTime = new JLabel();
		this._currTime.setSize(new Dimension(20, 50));
		this._numOfBodies = new JLabel();
		this._numOfBodies.setSize(new Dimension(10, 50));
		this._currLaws = new JLabel();
		this._currLaws.setSize(new Dimension(50, 50));
		JToolBar toolBar = new JToolBar();
		this.add(toolBar, BorderLayout.PAGE_START);
		toolBar.add(this._currTime);
		toolBar.addSeparator();
		toolBar.add(this._numOfBodies);
		toolBar.addSeparator();
		toolBar.add(this._currLaws);
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		this._currTime.setText("Time: " + time);
		this._numOfBodies.setText("Bodies: " + bodies.size());
		this._currLaws.setText("Laws: " + fLawsDesc);
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		this._currTime.setText("Time: " + time);
		this._numOfBodies.setText("Bodies: " + bodies.size());
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		this._numOfBodies.setText("Bodies: " + bodies.size());
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		this._currTime.setText("Time: " + time);
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		this._currLaws.setText("Laws: " + fLawsDesc);
	}

}
