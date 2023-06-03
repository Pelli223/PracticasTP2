package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {
	
	public BasicBodyBuilder(){
		super( "basic", "Basic body");
	}

	@Override
	protected Body createTheInstant(JSONObject jsonObject) throws IllegalArgumentException{
		try {
			Vector2D _p = new Vector2D(jsonObject.getJSONArray("p").getDouble(0), jsonObject.getJSONArray("p").getDouble(1));
			Vector2D _v = new Vector2D(jsonObject.getJSONArray("v").getDouble(0), jsonObject.getJSONArray("v").getDouble(1));
			return new Body (jsonObject.getString("id"), _v, _p, jsonObject.getDouble("m"));
		}
		catch (JSONException jexc) {
			throw new IllegalArgumentException("Invalid data cannot build the body");
		}
	}
	
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("id", "b1");
		data.put("p", new Vector2D().asJSONArray());
		data.put("v", new Vector2D(0.05e04, 0.0).asJSONArray());
		data.put("m", 5.97e24);
		return data;
	}

}
