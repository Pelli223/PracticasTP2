package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Builder<T> {
	
	protected String type;
	protected String desc;
	
	Builder(String type, String desc){
		this.type = type;
		this.desc = desc;
	}

	public T createInstance(JSONObject info) throws IllegalArgumentException {
		T b = null;
		if(this.type != null && this.type.equals(info.getString("type"))) {
			try {
				b = createTheInstant(info.getJSONObject("data"));
			}
			catch(JSONException jexcp) {
				throw new IllegalArgumentException();
			}
		}
		return b;
	}
	
	protected abstract T createTheInstant(JSONObject jsonObject);

	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		info.put("type", this.type);
		info.put("data", this.createData());
		info.put("desc", this.desc);
		return info;
	}
	
	protected JSONObject createData() {
		return new JSONObject();
	}

}
