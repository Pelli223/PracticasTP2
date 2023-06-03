package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	private double dt;
	private double at;
	private ForceLaws forceLaws;
	private List<Body> bodies;
	private List<SimulatorObserver> observers;
	
	public PhysicsSimulator(ForceLaws forceLaws, double dt) throws IllegalArgumentException {
		if(forceLaws == null) throw new IllegalArgumentException("Invalid ForceLaw to apply");
		else if(dt < 0) throw new IllegalArgumentException("Invalid time to apply");
		else {
			this.dt = dt;
			this.at = 0.0;
			this.forceLaws = forceLaws;
			this.bodies = new ArrayList<Body>();
			this.observers = new ArrayList<SimulatorObserver>();
		}
	}
	
	public void advance()  {
		for(Body i: bodies) {
			i.resetForce();
		}
		this.forceLaws.apply(this.bodies);
		for(Body i: bodies) {
			i.move(this.dt);
		}
		this.at += this.dt;
		for(SimulatorObserver o: this.observers) {
			o.onAdvance(this.bodies, this.at);
		}
	}
	
	public void addBody(Body b) throws IllegalArgumentException {
		if (this.bodies.contains(b)) throw new IllegalArgumentException("Body already exist");
		else {
			this.bodies.add(b);	
			for(SimulatorObserver o: this.observers) {
				o.onBodyAdded(this.bodies, b);
			}
		}
	}
	
	public void addObserver(SimulatorObserver o) throws IllegalArgumentException {
		if (this.observers.contains(o)) throw new IllegalArgumentException("Observer already exist");
		else {
			this.observers.add(o);
			o.onRegister(this.bodies, this.at, dt, this.forceLaws.toString());
		}
	}
	
	public void reset() {
		this.at = 0.0;
		this.bodies.clear();
		for(SimulatorObserver o: this.observers) {
			o.onReset(this.bodies, this.at, this.dt, this.forceLaws.toString());
		}
	}
	
	public JSONObject getState() {
		JSONObject simulator = new JSONObject();
		JSONArray bodiesArray = new JSONArray();
		simulator.put("time", this.at);
		for(Body i: bodies) {
			bodiesArray.put(i.getState());
		}
		simulator.put("bodies", bodiesArray);
		return simulator;
	}
	
	public String toString() {
		return this.getState().toString();
	}
	
	public void setDeltaTime(double dt) throws IllegalArgumentException{
		if(dt > 0) {
			this.dt = dt;
			for(SimulatorObserver o: this.observers) {
				o.onDeltaTimeChanged(dt);
			}
		}
		else throw new IllegalArgumentException("Invalid time to apply");
	}
	
	public void setForceLaws(ForceLaws forceLaws) throws IllegalArgumentException{
		if(forceLaws != null) {
			this.forceLaws = forceLaws;
			for(SimulatorObserver o: this.observers) {
				o.onForceLawsChanged(forceLaws.toString());
			}
		}
		else throw new IllegalArgumentException("Invalid ForceLaw to apply");
	}

}
