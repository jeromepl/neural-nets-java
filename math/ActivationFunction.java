package math;

public abstract class ActivationFunction extends Function1d {
	
	public static final ActivationFunction SIGMOID = new ActivationFunction() {
		@Override
		public double f(double x) {
			return 1.0 / (1.0 + Math.exp(-x));
		}
		
		@Override
		public Function1d derivative() {
			Function1d self = this;
			return new Function1d() {
				@Override
				public double f(double x) {
					double sigmoid = self.f(x);
					return sigmoid*(1 - sigmoid);
				}
			};
		}
	};
	
	public abstract Function1d derivative();
}
