package math;

import java.io.Serializable;

public class Matrix implements Serializable {
	
	private double[] values;
	private int nbRows, nbColumns;
	

	public Matrix(double[] values, int nbRows, int nbColumns) {
		if (values.length != nbRows * nbColumns)
			throw new RuntimeException("Invalid array size. Must be of size numRows * numColumns");
		
		this.values = values;
		this.nbRows = nbRows;
		this.nbColumns = nbColumns;
	}
	
	public Matrix(int nbRows, int nbColumns) { // Create an empty matrix
		this.nbRows = nbRows;
		this.nbColumns = nbColumns;
		values = new double[nbRows * nbColumns];
	}
	
	public int getNbRows() {
		return nbRows;
	}
	
	public int getNbColumns() {
		return nbColumns;
	}
	
	public double[] getValues() {
		return values;
	}
	
	public boolean withinRange(int row, int column) {
		return row < nbRows && row >= 0 && column < nbColumns && column >= 0;
	}
	
	public void setEntry(int row, int column, double value) {
		if(withinRange(row, column))
			values[row * nbColumns + column] = value;
		else
			throw new RuntimeException("Out of matrix bound");
	}
	
	public double getEntry(int row, int column) {
		if(withinRange(row, column))
			return values[row * nbColumns + column];
		else
			throw new RuntimeException("Out of matrix bound");
	}
	
	public void fill(double a){
		for(int i = 0; i < nbRows; i++) {
			for(int j = 0; j < nbColumns; j++) {
				setEntry(i, j, a);
			}
		}
	}
	
	/*** TRANSPOSE ***/
	
	public Matrix transpose() {
		if (nbRows == 1 || nbColumns == 1) // More efficient for vectors
			return new Matrix(values, nbColumns, nbRows);
		
		Matrix transposed = new Matrix(nbColumns, nbRows);
		transpose(this, transposed);
		
		return transposed;
	}
	
	public static void transpose(Matrix m, Matrix dest) {
		if (m.getNbRows() != m.getNbColumns() || m.getNbColumns() != m.getNbRows())
			throw new RuntimeException("Matrix size mismatch");
			
		for(int i = 0; i < m.getNbRows(); i++) {
			for(int j = 0; j < m.getNbColumns(); j++) {
				dest.setEntry(j, i, m.getEntry(i, j));
			}
		}
	}
	
	/*** ADDITION ***/
	
	public Matrix add(Matrix m) {
		Matrix newMatrix = new Matrix(nbRows, nbColumns);
		add(this, m, newMatrix);
		
		return newMatrix;
	}
	
	public void addSelf(Matrix m) {
		add(this, m, this);
	}
	
	public static void add(Matrix a, Matrix b, Matrix dest) {
		if (a.getNbRows() != b.getNbRows() || a.getNbColumns() != b.getNbColumns() || a.getNbRows() != dest.getNbRows() || a.getNbColumns() != dest.getNbColumns())
			throw new RuntimeException("Matrix size mismatch");
			
		for(int i = 0; i < a.getNbRows(); i++) {
			for(int j = 0; j < a.getNbColumns(); j++) {
				dest.setEntry(i, j, a.getEntry(i, j) + b.getEntry(i, j));
			}
		}
	}
	
	/*** SUBTRACTION ***/
	
	public Matrix subtract(Matrix m) {
		Matrix newMatrix = new Matrix(nbRows, nbColumns);
		subtract(this, m, newMatrix);
		
		return newMatrix;
	}
	
	public void subtractSelf(Matrix m) {
		subtract(this, m, this);
	}
	
	public static void subtract(Matrix a, Matrix b, Matrix dest) {
		if (a.getNbRows() != b.getNbRows() || a.getNbColumns() != b.getNbColumns() || a.getNbRows() != dest.getNbRows() || a.getNbColumns() != dest.getNbColumns())
			throw new RuntimeException("Matrix size mismatch");
		
		for(int i = 0; i < a.getNbRows(); i++) {
			for(int j = 0; j < a.getNbColumns(); j++) {
				dest.setEntry(i, j, a.getEntry(i, j) - b.getEntry(i, j));
			}
		}
	}
	
	/*** MULTIPLICATION ***/
	
	public double getMultipliedEntry(Matrix m, int row, int column) {
		return getMultipliedEntry(this, m, row, column);
	}
	
	public static double getMultipliedEntry(Matrix a, Matrix b, int row, int column) {
		if(!(row < a.getNbRows() && row >= 0 && column < b.getNbColumns() && column >= 0))
			throw new RuntimeException("Out of matrix bounds");
		
		double sum = 0;
		
		for(int i = 0; i < a.getNbColumns(); i++) {
			sum += a.getEntry(row, i) * b.getEntry(i, column);
		}
		
		return sum;
	}
	
	public Matrix multiply(double scalar) { //Scalar multiple
		Matrix newMatrix = new Matrix(nbRows, nbColumns);
		multiply(this, scalar, newMatrix);
		
		return newMatrix;
	}
	
	public void multiplySelf(double scalar) {
		multiply(this, scalar, this);
	}
	
	public static void multiply(Matrix a, double scalar, Matrix dest) {
		if (a.getNbRows() != dest.getNbRows() || a.getNbColumns() != dest.getNbColumns())
			throw new RuntimeException("Matrix size mismatch");
		
		for(int i = 0; i < a.getNbRows(); i++) {
			for(int j = 0; j < a.getNbColumns(); j++) {
				dest.setEntry(i, j, scalar * a.getEntry(i, j));
			}
		}
	}
	
	public Matrix multiply(Matrix m) {
		Matrix newMatrix = new Matrix(nbRows, m.getNbColumns());
		multiply(this, m, newMatrix);
		
		return newMatrix;
	}
	
	public static void multiply(Matrix a, Matrix b, Matrix dest) {
		if(a.getNbColumns() != b.getNbRows() || dest.getNbRows() != a.getNbRows() || dest.getNbColumns() != b.getNbColumns())
			throw new RuntimeException("Matrix size mismatch");
		
		for(int i = 0; i < a.getNbRows(); i++) {
			for(int j = 0; j < b.getNbColumns(); j++) {
				dest.setEntry(i, j, getMultipliedEntry(a, b, i, j));
			}
		}
	}
	
	/*** MULTIPLY WITH TRANSPOSE ***/

	// Transpose this matrix and multiply with m
	public Matrix multiplyTransposeSelf(Matrix m) {
		Matrix newMatrix = new Matrix(nbColumns, m.getNbColumns());
		multiplyTransposeA(this, m, newMatrix);

		return newMatrix;
	}
	
	public static void multiplyTransposeA(Matrix a, Matrix b, Matrix dest) {
		if(a.getNbRows() != b.getNbRows() || dest.getNbRows() != a.getNbColumns() || dest.getNbColumns() != b.getNbColumns())
			throw new RuntimeException("Matrix size mismatch");
		
		for(int i = 0; i < a.getNbColumns(); i++) {
			for(int j = 0; j < b.getNbColumns(); j++) {
				double total = 0;
				for (int k = 0; k < a.getNbRows(); k++) {
					total += a.getEntry(k, i) * b.getEntry(k, j);
				}
				dest.setEntry(i, j, total);
			}
		}
	}

	// Multiply this matrix with the transpose of m
	public Matrix multiplyTransposeM(Matrix m) {
		Matrix newMatrix = new Matrix(nbRows, m.getNbRows());
		multiplyTransposeB(this, m, newMatrix);

		return newMatrix;
	}
	
	public static void multiplyTransposeB(Matrix a, Matrix b, Matrix dest) {
		if(a.getNbColumns() != b.getNbColumns() || dest.getNbRows() != a.getNbRows() || dest.getNbColumns() != b.getNbRows())
			throw new RuntimeException("Matrix size mismatch");
		
		for(int i = 0; i < a.getNbRows(); i++) {
			for(int j = 0; j < b.getNbRows(); j++) {
				double total = 0;
				for (int k = 0; k < a.getNbColumns(); k++) {
					total += a.getEntry(i, k) * b.getEntry(j, k);
				}		
				dest.setEntry(i, j, total);
			}
		}
	}

	/*** HADAMARD PRODUCT ***/
	
	public Matrix hadamardProduct(Matrix m) {
		Matrix newMatrix = new Matrix(nbRows, nbColumns);
		hadamardProduct(this, m, newMatrix);
		
		return newMatrix;
	}
	
	public void hadamardProductSelf(Matrix m) {
		hadamardProduct(this, m, this);
	}
	
	public static void hadamardProduct(Matrix a, Matrix b, Matrix dest) {
		if(a.getNbRows() != b.getNbRows() || a.getNbColumns() != b.getNbColumns() || a.getNbRows() != dest.getNbRows() || a.getNbColumns() != dest.getNbColumns())
			throw new RuntimeException("Matrix size mismatch");
		
		for(int i = 0; i < a.getNbRows(); i++) {
			for(int j = 0; j < a.getNbColumns(); j++) {
				dest.setEntry(i, j, a.getEntry(i, j) * b.getEntry(i, j));
			}
		}
	}
	
	/*** STATIC MATRIX GENERATORS ***/
	
	public static Matrix getIdentity(int size) {
		Matrix newMatrix = new Matrix(size, size);
		
		for(int i = 0; i < size; i++) {
			newMatrix.setEntry(i, i, 1);
		}
		
		return newMatrix;
	}
	
	public static Matrix getZero(int size) {
		return new Matrix(size, size);
	}
	
	/*** VECTORIZATION ***/
	
	public Matrix vectorize(Function1d f) {
		Matrix newMatrix = new Matrix(nbRows, nbColumns);
		vectorize(this, f, newMatrix);
		
		return newMatrix;
	}
	
	public void vectorizeSelf(Function1d f){
		vectorize(this, f, this);
	}
	
	public static void vectorize(Matrix m, Function1d f, Matrix dest) {
		if(m.getNbRows() != dest.getNbRows() || m.getNbColumns() != dest.getNbColumns())
			throw new RuntimeException("Matrix size mismatch");
		
		for(int i = 0; i < m.getNbRows(); i++){
			for(int j = 0; j < m.getNbColumns(); j++){
				dest.setEntry(i, j, f.f(m.getEntry(i, j)));
			}
		}
	}
	
	/*** OBJECT METHODS ***/
	
	public boolean equals(Matrix m) {
		if(nbRows != m.getNbRows() || nbColumns != m.getNbColumns())
			return false;
		
		for(int i = 0; i < nbRows; i++) {
			for(int j = 0; j < nbColumns; j++) {
				if(getEntry(i, j) != m.getEntry(i, j))
					return false;
			}
		}
		
		return true;
	}
	
	public String toString() {
		String str = "[";
		
		for(int i = 0; i < nbRows; i++) {
			str += "{";
			for(int j = 0; j < nbColumns; j++) {
				str += getEntry(i, j);
				if(j != nbColumns - 1)
					str += ", ";
			}
			str += "}";
			if(i != nbRows - 1)
				str += ",\n";
		}
		
		str += "]";
		
		return str;
	}
	
	public double[] toFlatArray(){
		return values;
	}
}