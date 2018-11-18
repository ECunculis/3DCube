package Cube3D2;

import java.awt.Color;
import java.awt.Graphics2D;

public class Cube3D2 {

	private Vector3D[] Cube3DPoint;
	private Vector3D[] RotatedCube;
	private Vector3D[] ProjectedCube;
	private double size;
	private double pointSize;

	private double angle, angleSpeed;
	private double distance;
	private Matrix xRotMatrix, yRotMatrix, zRotMatrix, ProjectionMatrix;

	public Cube3D2() {
		size = 1000;
		angleSpeed = 0.01;
		pointSize = 6;
		distance = 6;

		Cube3DPoint = new Vector3D[8];
		RotatedCube = new Vector3D[8];
		ProjectedCube = new Vector3D[8];

		Cube3DPoint[0] = new Vector3D(size, size, size);
		Cube3DPoint[1] = new Vector3D(size, -size, size);
		Cube3DPoint[2] = new Vector3D(-size, -size, size);
		Cube3DPoint[3] = new Vector3D(-size, size, size);
		Cube3DPoint[4] = new Vector3D(size, size, -size);
		Cube3DPoint[5] = new Vector3D(size, -size, -size);
		Cube3DPoint[6] = new Vector3D(-size, -size, -size);
		Cube3DPoint[7] = new Vector3D(-size, size, -size);

		update();
	}

	public void update() {
		angle += angleSpeed;
		double[][] temp = new double[][] {
				{ 1, 0, 0 },
				{ 0, Math.cos(angle), -Math.sin(angle) },
				{ 0, Math.sin(angle), Math.cos(angle) }
		};

		xRotMatrix = new Matrix(temp);

		temp = new double[][] {
				{ Math.cos(angle), 0, Math.sin(angle) },
				{ 0, 1, 0 },
				{ -Math.sin(angle), 0, Math.cos(angle) }
		};

		yRotMatrix = new Matrix(temp);

		temp = new double[][] {
				{ Math.cos(angle), -Math.sin(angle), 0 },
				{ Math.sin(angle), Math.cos(angle), 0 },
				{ 0, 0, 1 }
		};

		zRotMatrix = new Matrix(temp);

		// Rotate the cube
		for (int i = 0; i < Cube3DPoint.length; i++) {
			RotatedCube[i] = yRotMatrix.vector3DMul(Cube3DPoint[i]);
			RotatedCube[i] = zRotMatrix.vector3DMul(RotatedCube[i]);
			RotatedCube[i] = xRotMatrix.vector3DMul(RotatedCube[i]);
		}

		// Perspective projection
		for (int i = 0; i < RotatedCube.length; i++) {
			double z = 1 / (distance - (RotatedCube[i].getZ() / size));
			temp = new double[][] {
					{ z, 0, 0 },
					{ 0, z, 0 },
					{ 0, 0, 0 }
			};
			ProjectionMatrix = new Matrix(temp);
			ProjectedCube[i] = ProjectionMatrix.vector3DMul(RotatedCube[i]);
		}
		
	}

	public void draw(Graphics2D g2d) {
		g2d.translate(Window.WIDTH / 2, Window.HEIGHT / 2);
		g2d.setColor(Color.BLACK);

		for (Vector3D p : ProjectedCube) {
			g2d.fillOval((int) (p.getX() - pointSize / 2), (int) (p.getY() - pointSize / 2), (int) pointSize, (int) pointSize);
		}
		
		// Draw lines
		for (int i = 0; i < RotatedCube.length; i++) {
			for (int j = 0; j < RotatedCube.length; j++) {
				if (Math.round(RotatedCube[i].distance(RotatedCube[j])) == size * 2) {
					g2d.drawLine((int) ProjectedCube[i].getX(),(int) ProjectedCube[i].getY(),(int) ProjectedCube[j].getX(),(int) ProjectedCube[j].getY());
					
//					// Fill one side of the cube blue
//
//					int[] xCoordinates = new int[4];
//					int[] yCoordinates = new int[4];
//					
//					xCoordinates[1] = (int) ProjectedCube[0].getX();
//					xCoordinates[0] = (int) ProjectedCube[1].getX();
//					xCoordinates[2] = (int) ProjectedCube[3].getX();
//					xCoordinates[3] = (int) ProjectedCube[2].getX();
//
//					yCoordinates[1] = (int) ProjectedCube[0].getY();
//					yCoordinates[0] = (int) ProjectedCube[1].getY();
//					yCoordinates[2] = (int) ProjectedCube[3].getY();
//					yCoordinates[3] = (int) ProjectedCube[2].getY();
//
//					g2d.setColor(Color.blue);
//					g2d.fillPolygon(xCoordinates, yCoordinates, 4);
//				
//					g2d.fillPolygon(xCoordinates, yCoordinates, 4);
				}
			}
		}

	}

}
