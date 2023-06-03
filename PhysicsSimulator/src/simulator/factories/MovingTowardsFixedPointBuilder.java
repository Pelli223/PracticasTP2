package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {
	
	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Moving towadrs fixed point force");
	}

	@Override
	protected ForceLaws createTheInstant(JSONObject jsonObject) {
		double g = jsonObject.has("g") ? jsonObject.getDouble("g") : 9.81;
		Vector2D c = jsonObject.has("c") ? new Vector2D(jsonObject.getJSONArray("c").getDouble(0), jsonObject.getJSONArray("c").getDouble(1)) : new Vector2D();
		return new MovingTowardsFixedPoint(g, c);
	}
	
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("c", new Vector2D());
		data.put("g", 9.81);
		return data;
	}
}
