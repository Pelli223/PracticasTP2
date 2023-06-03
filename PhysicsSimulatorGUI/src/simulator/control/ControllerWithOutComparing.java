package simulator.control;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONObject;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;

public class ControllerWithOutComparing extends Controller{

	public ControllerWithOutComparing(Factory<Body> bodyBuilder, PhysicsSimulator simulator, Factory <ForceLaws> forceBuilder) {
		super(bodyBuilder, simulator, forceBuilder);
	}

	@Override
	public void run(int steps, OutputStream out) throws NotEqualStateException{
		JSONObject current = null;
		if(out == null) {
			out = new OutputStream() {
				public void write(int b) throws IOException {}
			};
		}
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		current = this.simulator.getState();
		p.println(current);
		for(int i = 1; i <= steps; i++) {
			p.print(",");
			this.simulator.advance();
			current = this.simulator.getState();
			p.println(current);
		}
		p.println("]");
		p.println("}");
	}
}
