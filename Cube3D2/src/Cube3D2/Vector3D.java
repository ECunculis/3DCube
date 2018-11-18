package Cube3D2;

public class Vector3D {
	
	private double x, y ,z;
	
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	// Returns distance between two 3D points
	public double distance(Vector3D v) {
		return Math.sqrt((x - v.getX()) * (x - v.getX()) + (y - v.getY()) * (y - v.getY()) + (z - v.getZ()) * (z - v.getZ()));
	}
	

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	

}
