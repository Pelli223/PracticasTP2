package simulator.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver{
	
	private List<Body> _bodies;
	private static String[] _colNames = {"id", "Mass", "Position", "Velocity", "Force"};
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return _colNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return _bodies == null ? 0 : _bodies.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		Object s = null;
		switch (arg1) {
		case 0:
			s = _bodies.get(arg0).getId();
			break;
		case 1:
			s = _bodies.get(arg0).getMass();
			break;
		case 2:
			s =_bodies.get(arg0).getPosition();
			break;
		case 3:
			s = _bodies.get(arg0).getVelocity();
			break;
		case 4:
			s = _bodies.get(arg0).getForce();
			break;
		}
		return s;
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

}
