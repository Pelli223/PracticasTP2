package simulator.model;

import simulator.misc.Vector2D;

public class MassLosingBody extends Body{
	protected double lossFactor;
	protected double lossFrecuency;
	protected double c;
	
	public MassLosingBody(String id, Vector2D _v, Vector2D _p, double m, double lossFactor, double lossFrecuency) throws  IllegalArgumentException {
		super(id, _v, _p, m);
		c = 0.0;
		if(lossFactor < 0 || lossFactor > 1) throw new IllegalArgumentException("Invalid lossFactor value");
		if(lossFrecuency < 0) throw new IllegalArgumentException("Invalid lossFrecuency value");
		this.lossFactor = lossFactor;
		this.lossFrecuency = lossFrecuency;
	}
	
	void move(double t){
		super.move(t);
		this.c += t;
		if (this.c >= this.lossFrecuency) {
			this.m = this.m * (1 - this.lossFactor);
			this.c = 0.0;
		}
	}
}
