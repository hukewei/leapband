package fr.utc.leapband.utilities;

public class Point {
	public double X;
	public double Y;
	public double Z;
	
	public Point getVector(Point p) {
		Point res = new Point();
		res.X = p.X - this.X;
		res.Y = p.Y - this.Y;
		res.Z = p.Z - this.Z;
		
		return res;
	}
}
