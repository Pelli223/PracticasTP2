package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.PhysicsSimulator;

public abstract class Controller {
	
	protected Factory <Body> bodyBuilder;
	protected PhysicsSimulator simulator;
	
	public Controller(Factory<Body> bodyBuilder, PhysicsSimulator simulator) {
		this.bodyBuilder = bodyBuilder;
		this.simulator = simulator;
	}
	
	public abstract void run(int steps, OutputStream out) throws NotEqualStateException;
	
	public void loadBodies(InputStream in) throws JSONException{
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		for(int i = 0; i < jsonInput.getJSONArray("bodies").length(); i++) {
			this.simulator.addBody(this.bodyBuilder.createInstance(jsonInput.getJSONArray("bodies").getJSONObject(i)));
		}
	}
}
