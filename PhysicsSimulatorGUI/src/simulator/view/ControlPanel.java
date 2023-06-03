package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import org.json.JSONException;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {
	
	private Controller _cntrl;
	private boolean stopped;
	private JSpinner spinner;
	private JToolBar toolBar;
	private JTextField setDelta;
	private JFileChooser fc;
	private JButton fileChooser, setForceLaw, run, stop, exit;
	private ForceLawsSelector laws;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4896281178907668756L;
	
	ControlPanel(Controller cntrl, JFrame mainWindow){
		this._cntrl = cntrl;
		this.stopped = true;
		initGUI(mainWindow);
		fc = new JFileChooser();
		this._cntrl.addObserver(this);
	}

	private void initGUI(JFrame mainWindow) {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout());
		this.toolBar = new JToolBar();
		this.laws = new ForceLawsSelector(mainWindow, this._cntrl.getForceLawsInfo());
		this.add(this.toolBar, BorderLayout.PAGE_START);
		JLabel steps = new JLabel("Steps:");
		this.initSpinner();
		JLabel deltaTime = new JLabel("Delta-Time:");
		this.initDeltaTime();
		this.initFileChooser();
		this.initSetForceLaws();
		this.initRunButton();
		this.initStop();
		this.initExit();
		toolBar.add(fileChooser);
		toolBar.addSeparator();
		toolBar.add(setForceLaw);
		toolBar.addSeparator();
		toolBar.add(run);
		toolBar.add(stop);
		toolBar.add(steps);
		toolBar.add(spinner);
		toolBar.add(deltaTime);
		toolBar.add(setDelta);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.addSeparator();
		toolBar.add(exit, BorderLayout.EAST);
	}
	
	private void run_sim(int n) {
		if(n > 0 && !stopped) {
			try {
				_cntrl.run(1, null);
			} catch(Exception e) {
				JOptionPane.showMessageDialog(this, "Error running the program", "Error", JOptionPane.ERROR_MESSAGE);
				stopped = true;
				this.enableComponents(true);
				return;
			}
			SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
					run_sim(n-1);
				}
			});
		} else {
			stopped = true;
			this.enableComponents(true);
		}
	}
	
	private void initSpinner() {
		spinner = new JSpinner (new SpinnerNumberModel(1000,100,1000000,100));
		spinner.setMinimumSize(new Dimension(80, 30));
		spinner.setMaximumSize(new Dimension(200, 30));
		spinner.setPreferredSize(new Dimension(100, 30));
	}
	
	private void initDeltaTime() {
		setDelta = new JTextField();
		setDelta.setMinimumSize(new Dimension(80, 30));
		setDelta.setMaximumSize(new Dimension(200, 30));
		setDelta.setPreferredSize(new Dimension(100, 30));
		setDelta.setEditable(true);
	}
	
	private void initFileChooser() {
		fileChooser = new JButton();
		fileChooser.setIcon(new ImageIcon("resources/icons/open.png"));
		fileChooser.setToolTipText("Choose a file to load the bodies");
		fileChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int ret = fc.showOpenDialog(null);
				if(ret == JFileChooser.APPROVE_OPTION) {
					_cntrl.reset();
					try {
						_cntrl.loadBodies(new FileInputStream(fc.getSelectedFile()));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Error invalid file to load bodies", "Error", JOptionPane.ERROR_MESSAGE);
					}
					catch(FileNotFoundException fe) {
						JOptionPane.showMessageDialog(null, "Error loading the file", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	
	private void initSetForceLaws() {
		setForceLaw = new JButton();
		setForceLaw.setIcon(new ImageIcon("resources/icons/physics.png"));
		setForceLaw.setToolTipText("Select the forceLaw to apply");
		setForceLaw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int n = laws.open();
				if(n == 1) {
					try {
						JSONObject info = laws.getJSON();
						_cntrl.setForceLaws(info);
					}
					catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "Error invalid value for the force law", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	
	private void initRunButton() {
		run = new JButton();
		run.setIcon(new ImageIcon("resources/icons/run.png"));
		run.setToolTipText("Run");
		run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				enableComponents(false);
				stop.setEnabled(true);
				stopped = false;
				try {
					_cntrl.setDeltaTime(Double.parseDouble(setDelta.getText()));
				}
				catch(NumberFormatException ne) {
					JOptionPane.showMessageDialog(null, "Error invalid value for DeltaTime: " + setDelta.getText(), "Error", JOptionPane.ERROR_MESSAGE);
					setDelta.setText(Double.toString(10000.0));
					stopped = true;
				}
				run_sim(Integer.parseInt(spinner.getValue().toString()));
			}
		});
	}
	
	private void initStop() {
		stop = new JButton();
		stop.setIcon(new ImageIcon("resources/icons/stop.png"));
		stop.setToolTipText("Stop");
		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				stopped = true;
			}
		});
	}
	
	private void initExit() {
		exit = new JButton();
		exit.setIcon(new ImageIcon("resources/icons/exit.png"));
		exit.setToolTipText("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				quit();
			}
		});
	}
	
	private void enableComponents (boolean enable) {
		Component[] components = this.toolBar.getComponents();
		for(Component component: components) {
			component.setEnabled(enable);
		}
	}
	
	private void quit() {
		int n = JOptionPane.showOptionDialog(new JFrame(),
				 "Are sure you want to quit?", "Quit",
				 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				 null, null);
		if (n == 0) System.exit(0);
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		this.setDelta.setText(Double.toString(dt));
		setDelta.setEditable(true);
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		this.setDelta.setText(Double.toString(dt));
		setDelta.setEditable(true);
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		this.setDelta.setText(Double.toString(dt));
		setDelta.setEditable(true);
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

}
