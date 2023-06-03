package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public abstract class Controller {
	
	protected Factory <Body> bodyBuilder;
	protected PhysicsSimulator simulator;
	protected Factory <ForceLaws> forceBuilder;
	
	public Controller(Factory<Body> bodyBuilder, PhysicsSimulator simulator, Factory <ForceLaws> forceBuilder) {
		this.bodyBuilder = bodyBuilder;
		this.simulator = simulator;
		this.forceBuilder = forceBuilder;
	}
	
	public abstract void run(int steps, OutputStream out) throws NotEqualStateException;
	
	public void loadBodies(InputStream in) throws JSONException{
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		for(int i = 0; i < jsonInput.getJSONArray("bodies").length(); i++) {
			this.simulator.addBody(this.bodyBuilder.createInstance(jsonInput.getJSONArray("bodies").getJSONObject(i)));
		}
	}
	
	public void reset() {
		this.simulator.reset();
	}
	
	public void addObserver(SimulatorObserver o) {
		this.simulator.addObserver(o);
	}
	
	public List<JSONObject> getForceLawsInfo(){
		return forceBuilder.getInfo();
	}
	
	public void setDeltaTime(double dt) {
		this.simulator.setDeltaTime(dt);
	}
	
	public void setForceLaws(JSONObject info) {
		this.simulator.setForceLaws(this.forceBuilder.createInstance(info));
	}
}
