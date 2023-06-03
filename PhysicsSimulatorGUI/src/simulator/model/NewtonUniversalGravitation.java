package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
	
	private double G;
	
	public NewtonUniversalGravitation() {
		this.G = 6.67e10-11;
	}
	
	public NewtonUniversalGravitation(double G) {
		this.G = G;
	}
	
	private Vector2D force(Body a, Body b) {
		 Vector2D delta = b.getPosition().minus(a.getPosition());
		 double dist = delta.magnitude();
		 double magnitude = dist>0 ? (G * a.getMass() * b.getMass()) / (dist * dist) : 0.0;
		 return delta.direction().scale(magnitude);
	}

	@Override
	public void apply(List<Body> bs) {
		for(Body i: bs) {
			for(Body j: bs) {
				if(!i.equals(j)) i.addForce(this.force(i, j));
			}
		}
	}
	
	public String toString() {
		return "Newton's Universal Gravitation with G = " + G ;
	}

}
