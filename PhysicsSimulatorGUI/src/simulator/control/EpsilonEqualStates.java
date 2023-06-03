package simulator.control;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator{
	
	private JSONObject body1;
	private JSONObject body2;
	private double eps;
	
	public EpsilonEqualStates(double eps) {
		this.eps = eps;
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
			
			while( (i < s1.getJSONArray("bodies").length()) && equals) {
				Vector2D _p1 = new Vector2D (s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("p").getDouble(0), s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("p").getDouble(1));
				Vector2D _p2 = new Vector2D (s2.getJSONArray("bodies").getJSONObject(i).getJSONArray("p").getDouble(0), s2.getJSONArray("bodies").getJSONObject(i).getJSONArray("p").getDouble(1));
				Vector2D _f1 = new Vector2D (s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("f").getDouble(0), s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("f").getDouble(1));
				Vector2D _f2 = new Vector2D (s2.getJSONArray("bodies").getJSONObject(i).getJSONArray("f").getDouble(0), s2.getJSONArray("bodies").getJSONObject(i).getJSONArray("f").getDouble(1));
				Vector2D _v1 = new Vector2D (s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("v").getDouble(0), s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("v").getDouble(1));
				Vector2D _v2 = new Vector2D (s2.getJSONArray("bodies").getJSONObject(i).getJSONArray("v").getDouble(0), s2.getJSONArray("bodies").getJSONObject(i).getJSONArray("v").getDouble(1));
				double m1 = s1.getJSONArray("bodies").getJSONObject(i).getDouble("m");
				double m2 = s2.getJSONArray("bodies").getJSONObject(i).getDouble("m");
				if(!(s1.getJSONArray("bodies").getJSONObject(i).getString("id").equals(s2.getJSONArray("bodies").getJSONObject(i).getString("id")))) {
					equals = false;
					this.body1 = s1.getJSONArray("bodies").getJSONObject(i);
					this.body2 = s2.getJSONArray("bodies").getJSONObject(i);
				}
				else if(Math.abs(m1 - m2) > this.eps) {
					equals = false;
					this.body1 = s1.getJSONArray("bodies").getJSONObject(i);
					this.body2 = s2.getJSONArray("bodies").getJSONObject(i);
				}
				else if((_p1.distanceTo(_p2)) > this.eps) {
					equals = false;
					this.body1 = s1.getJSONArray("bodies").getJSONObject(i);
					this.body2 = s2.getJSONArray("bodies").getJSONObject(i);
				}
				else if(_f1.distanceTo(_f2) > this.eps) {
					equals = false;
					this.body1 = s1.getJSONArray("bodies").getJSONObject(i);
					this.body2 = s2.getJSONArray("bodies").getJSONObject(i);
				}
				else if(_v1.distanceTo(_v2) > this.eps) {
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
