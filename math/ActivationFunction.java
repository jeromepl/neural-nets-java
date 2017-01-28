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
	
	public static final ActivationFunction TANH = new ActivationFunction() {
		@Override
		public double f(double x) {
			return Math.tanh(x);
		}
		
		@Override
		public Function1d derivative() {
			return new Function1d() {
				@Override
				public double f(double x) {
					return 4 * Math.pow(Math.cosh(x), 2) / Math.pow(Math.cosh(2*x) + 1, 2); // sech(x)^2
				}
			};
			
		}
	};
	
	
	public static final ActivationFunction RELU = new ActivationFunction() {
		@Override
		public double f(double x) {
			return (x > 0)? x: 0;
		}
		
		@Override
		public Function1d derivative() {
			return new Function1d() {
				@Override
				public double f(double x) {
					return (x > 0)? 1: 0;
				}
			};
			
		}
	};
	
	public abstract Function1d derivative();
}
