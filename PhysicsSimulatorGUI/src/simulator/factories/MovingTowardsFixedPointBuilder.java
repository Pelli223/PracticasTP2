package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {
	
	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Moving towadrs a fixed point force");
	}

	@Override
	protected ForceLaws createTheInstant(JSONObject jsonObject) {
		double g = jsonObject.has("g") ? jsonObject.getDouble("g") : 9.81;
		Vector2D c = jsonObject.has("c") ? new Vector2D(jsonObject.getJSONArray("c").getDouble(0), jsonObject.getJSONArray("c").getDouble(1)) : new Vector2D();
		return new MovingTowardsFixedPoint(g, c);
	}
	
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("c", "the point towards which bodies move (a json list of 2 numbers, e.g., [100.0,50.0])");
		data.put("g", "the length of the acceleration vector (a number)");
		return data;
	}
}
