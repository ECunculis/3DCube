package Cube3D2;


public class Matrix {
	
	private double[][] matrix;
	private int numOfColumns;
	private int numOfRows;
	
	public Matrix(double[][] matrix) {
		this.matrix = matrix;
		numOfRows = matrix.length;
		numOfColumns = matrix[0].length;
	}
	
	public Matrix matrixMul(Matrix m) {
		if (numOfColumns == m.getNumOfRows()) {
			double[][] newMatrix = new double[m.getNumOfColumns()][numOfRows];
			// For each row of the 1st matrix
			for (int i = 0; i < numOfRows; i++) {
				// For each column of the 2nd matrix
				for (int j = 0; j < m.getNumOfColumns(); j++) {
					// Each element in 1st matrix row multiply with each element in 2nd matrix column
					for (int k = 0; k < numOfColumns; k++) {
						newMatrix[j][k] += matrix[i][k] * m.getMatrixElement(k, j);
					}
				}
			}
			return new Matrix(newMatrix);
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	// Multiplies Matrix to 3D vector and returns new vector
	public Vector3D vector3DMul(Vector3D v) {
		double x, y, z;
		x = matrix[0][0] * v.getX() + matrix[0][1] * v.getY() + matrix[0][2] * v.getZ();
		y = matrix[1][0] * v.getX() + matrix[1][1] * v.getY() + matrix[1][2] * v.getZ();
		z = matrix[2][0] * v.getX() + matrix[2][1] * v.getY() + matrix[2][2] * v.getZ();
		return new Vector3D(x, y, z);
	}
	
	public void print() {
		for (int i = 0; i < matrix.length; i++) {
			System.out.println();
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.print(matrix[i][j]+ " ");
			}
		}
	}
	
	public double getMatrixElement(int i, int j) {
		return matrix[i][j];
	}
	
	public double[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}

	public int getNumOfColumns() {
		return numOfColumns;
	}

	public void setNumOfColumns(int numOfColumns) {
		this.numOfColumns = numOfColumns;
	}

	public int getNumOfRows() {
		return numOfRows;
	}

	public void setNumOfRows(int numOfRows) {
		this.numOfRows = numOfRows;
	}
	

}
