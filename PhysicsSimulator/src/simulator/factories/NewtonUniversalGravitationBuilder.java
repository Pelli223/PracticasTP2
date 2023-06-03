package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {
	
	public NewtonUniversalGravitationBuilder() {
		super ("nlug", "Newton’s law of universal gravitation");
	}

	@Override
	protected ForceLaws createTheInstant(JSONObject jsonObject) {
		double G = jsonObject.has("G") ? jsonObject.getDouble("G") : 6.67E-11;
		return new NewtonUniversalGravitation(G);
	}
	
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("G", 6.67E-11);
		return data;
	}
}
