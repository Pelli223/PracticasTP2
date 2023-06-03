package simulator.model;

import javax.swing.table.AbstractTableModel;

import org.json.JSONObject;

public class ParametersTableModel extends AbstractTableModel{
	
	private String [][] _data;
	private String[] _colNames = {"Key", "Value", "Description"};
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ParametersTableModel(JSONObject data) {
		setParameters(data);
	}
	
	private void init(JSONObject data) {
		// TODO Auto-generated method stub
		for (int i = 0; i < data.names().length(); i++) {
			_data[i][0] = data.names().getString(i);
			_data[i][1] = "";
			_data[i][2] = data.getString(data.names().getString(i));	
		}
		fireTableDataChanged();
	}
	
	public boolean isCellEditable(int row, int column) {
		return column == 1;
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
		return _data.length;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return _data[arg0][arg1];
	}
	
	public void setValueAt(Object value, int row, int col) {
		_data[row][col] = value.toString();
	}
	
	public String getData() {
		StringBuilder s = new StringBuilder();
		s.append('{');
		for (int i = 0; i < _data.length; i++) {
			if (!_data[i][0].isEmpty() && !_data[i][1].isEmpty()) {
				s.append('"');
				s.append(_data[i][0]);
				s.append('"');
				s.append(':');
				s.append(_data[i][1]);
				s.append(',');
			}
		}

		if (s.length() > 1)
			s.deleteCharAt(s.length() - 1);
		s.append('}');
		return s.toString();
	}
	
	public void setParameters(JSONObject data) {
		if(data.names() != null) {
			_data = new String[data.names().length()][_colNames.length];
			init(data);
		}
		else {
			_data = new String[0][_colNames.length];
			fireTableDataChanged();	
		}
	}
}
