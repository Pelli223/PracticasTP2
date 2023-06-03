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
	
	public PhysicsSimulator(ForceLaws forceLaws, double dt) throws IllegalArgumentException {
		if(forceLaws == null) throw new IllegalArgumentException("Invalid ForceLaw to apply");
		else if(dt < 0) throw new IllegalArgumentException("Invalid time to apply");
		else {
			this.dt = dt;
			this.at = 0.0;
			this.forceLaws = forceLaws;
			this.bodies = new ArrayList<Body>();
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
	}
	
	public void addBody(Body b) throws IllegalArgumentException {
		if(this.bodies.isEmpty()) this.bodies.add(b);
		else {
			if(this.bodies.contains(b)) throw new IllegalArgumentException("Body already exist");
			else this.bodies.add(b);	
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

}
