package math;

public abstract class Function1d {
	
	public static final double H = 1e-5;
	
	public abstract double f(double x);
	
	public Function1d derivative() {
		return new Function1d() {
			@Override
			public double f(double x) {
				return (f(x + H) - f(x - H)) / (2 * H);
			}
		};
	}
}
