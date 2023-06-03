package simulator.control;

import org.json.JSONObject;

public class MassEqualState implements StateComparator{
	
	private JSONObject body1;
	private JSONObject body2;
	
	public MassEqualState() {
	}

	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		int i = 0;
		boolean equals = true;
		if(s1.getDouble("time") != s2.getDouble("time")) {
			equals = false;
			this.body1 = s1.getJSONArray("bodies").getJSONObject(i);
			this.body2 = s2.getJSONArray("bodies").getJSONObject(i);
		}
		else if(s1.getJSONArray("bodies").length() != s2.getJSONArray("bodies").length()) {
			equals = false;
			this.body1 = s1.getJSONArray("bodies").getJSONObject(s1.getJSONArray("bodies").length() - 1);
			this.body2 = s2.getJSONArray("bodies").getJSONObject(s2.getJSONArray("bodies").length() - 1);
		}
		else {
			while ((i < s2.getJSONArray("bodies").length()) && equals) {
				if(s1.getJSONArray("bodies").getJSONObject(i).getDouble("m") != s2.getJSONArray("bodies").getJSONObject(i).getDouble("m")) {
					equals = false;
					this.body1 = s1.getJSONArray("bodies").getJSONObject(i);
					this.body2 = s2.getJSONArray("bodies").getJSONObject(i);
				}
				else if (!(s1.getJSONArray("bodies").getJSONObject(i).getString("id").equals(s2.getJSONArray("bodies").getJSONObject(i).getString("id")))) {
					equals = false;
					this.body1 = s1.getJSONArray("bodies").getJSONObject(i);
					this.body2 = s2.getJSONArray("bodies").getJSONObject(i);
				}
				else i++;
			}
		}
		return equals;
	}
	
	public JSONObject getBody1() {
		return this.body1;
	}
	
	public JSONObject getBody2() {
		return this.body2;
	}
}
