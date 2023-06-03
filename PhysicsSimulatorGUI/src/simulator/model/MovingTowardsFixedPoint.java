package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
	
	private double g;
	private Vector2D _c;
	
	public MovingTowardsFixedPoint() {
		this.g = 9.81;
		this._c = new Vector2D();
	}
	
	public MovingTowardsFixedPoint(double g, Vector2D _c) {
		this.g = g;
		this._c = _c;
	}
	
	@Override
	public void apply(List<Body> bs) {
		for(Body b: bs) { 
			b.addForce(this._c.minus(b.getPosition()).direction().scale(g * b.getMass()));
		}
	}
	
	public String toString() {
		return "Moving Towards c- " + this._c +"with constant acceleration -" + this.g;
	}
}
