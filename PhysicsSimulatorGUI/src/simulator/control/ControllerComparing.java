package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;

public class ControllerComparing extends Controller{
	
	private StateComparator stComp;
	private InputStream expOut;

	public ControllerComparing(Factory<Body> bodyBuilder, PhysicsSimulator simulator, InputStream expOut, StateComparator stComp, Factory <ForceLaws> forceBuilder) {
		super(bodyBuilder, simulator, forceBuilder);
		this.stComp = stComp;
		this.expOut = expOut;
	}

	@Override
	public void run(int steps, OutputStream out) throws NotEqualStateException{
		JSONObject jsonExpOut = new JSONObject(new JSONTokener(this.expOut));
		JSONObject current = null;
		JSONObject exp = null;
		if(out == null) {
			out = new OutputStream() {
				public void write(int b) throws IOException {}
			};
		}
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		current = this.simulator.getState();
		exp = jsonExpOut.getJSONArray("states").getJSONObject(0);
		p.println(current);
		if(!this.stComp.equal(exp, current)) throw new NotEqualStateException(exp, current, stComp.getBody1(), stComp.getBody2(), 0);
		for(int i = 1; i <= steps; i++) {
			p.print(",");
			this.simulator.advance();
			current = this.simulator.getState();
			exp = jsonExpOut.getJSONArray("states").getJSONObject(i);
			p.println(current);
			if(!this.stComp.equal(exp, current)) throw new NotEqualStateException(exp, current, stComp.getBody1(), stComp.getBody2(), i);
		}
		p.println("]");
		p.println("}");
	}

}
