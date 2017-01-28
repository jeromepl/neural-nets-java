package math;

public abstract class Function3M {
	
	public abstract Matrix f(Matrix m1, Matrix m2, Matrix m3);
	
	public Matrix derivative(Matrix m1, Matrix m2, Matrix m3) {
		return f(m1, m2, m3);
	}
}
