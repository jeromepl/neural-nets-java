package math;

public abstract class Function {
	
	public static final double H = 1e-5;
	
	public abstract double f(double x);
	
	public double derivative(double x) {
		return (f(x + H) - f(x)) / H;
	}
}
