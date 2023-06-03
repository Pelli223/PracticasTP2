package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLosingBody;

public class MassLosingBodyBuilder extends Builder<Body> {
	
	public MassLosingBodyBuilder() {
		super ("mlb", "Mass losing body");
	}

	@Override
	protected Body createTheInstant(JSONObject jsonObject) throws IllegalArgumentException {
		try {
			Vector2D _p = new Vector2D(jsonObject.getJSONArray("p").getDouble(0), jsonObject.getJSONArray("p").getDouble(1));
			Vector2D _v = new Vector2D(jsonObject.getJSONArray("v").getDouble(0), jsonObject.getJSONArray("v").getDouble(1));
			return new MassLosingBody (jsonObject.getString("id"), _v, _p, jsonObject.getDouble("m"), jsonObject.getDouble("factor"), jsonObject.getDouble("freq"));
		}
		catch(JSONException jexc) {
			throw new IllegalArgumentException ("Invalid data cannot create MassLosingBody");
		}
	}
	
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("id", "b1");
		data.put("p", new Vector2D());
		data.put("v", new Vector2D(0.05e04, 0.0));
		data.put("m", 5.97e24);
		data.put("freq", 1e3);
		data.put("factor", 1e-3);
		return data;
	}

}
