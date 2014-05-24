package Utilities;


import javax.vecmath.Point3d;

import com.leapmotion.leap.Vector;

public class Movement {
	private Point3d pos;
	private double speed;
	private Vector direction;
	
	public Point3d getPos() {
		return pos;
	}
	public void setPos(Point3d pos) {
		this.pos = pos;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public Vector getDirection() {
		return direction;
	}
	public void setDirection(Vector direction) {
		this.direction = direction;
	}
}
