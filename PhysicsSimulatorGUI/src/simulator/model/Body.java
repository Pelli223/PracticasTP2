package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {
	protected String id;
	protected Vector2D _v;
	protected Vector2D _f;
	protected Vector2D _p;
	protected double m;
	
	public Body(String id, Vector2D _v, Vector2D _p, double m) {
		this.id = id;
		this._v = new Vector2D(_v);
		this._p = new Vector2D(_p);
		this._f = new Vector2D();
		this.m = m;
	}
	
	void addForce(Vector2D f) {
		this._f = this._f.plus(f);
	}
	
	void resetForce() {
		this._f = new Vector2D();
	}
	
	void move(double t) {
		Vector2D _a;
		if(m != 0)  _a = _f.scale(1.0/this.m);
		else _a = new Vector2D();
		this._p =  _p.plus(_v.scale(t).plus(_a.scale(0.5 * t * t)));
		this._v = this._v.plus(_a.scale(t));
	}
	
	public boolean equals(Object obj) {
		if(this.id.equals(((Body) obj).getId())) return true;
		else return false;
	}
	
	public JSONObject getState() {
		JSONObject body = new JSONObject();
		body.put("id",this.id);
		body.put("m", this.m);
		body.put("p", this._p.asJSONArray());
		body.put("v", this._v.asJSONArray());
		body.put("f", this._f.asJSONArray());
		return body;
	}
	
	public String toString() {
		return getState().toString();
	}
	
	public String getId() {
		return this.id;
	}
	
	public Vector2D getVelocity() {
		return this._v;
	}
	
	public Vector2D getForce() {
		return this._f;
	}
	
	public Vector2D getPosition() {
		return this._p;
	}
	
	public double getMass() {
		return this.m;
	}
}
