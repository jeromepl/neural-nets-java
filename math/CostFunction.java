package math;

public abstract class CostFunction extends Function3M {
	
	public static final CostFunction QUADRATIC = new CostFunction() {
		@Override
		public Matrix f(Matrix outputZ, Matrix calculatedResult, Matrix expectedResult) {
			Matrix cost = calculatedResult.subtract(expectedResult);
			cost.hadamardProductSelf(outputZ.vectorize(ActivationFunction.SIGMOID));
			return cost;
		}
	};
	
	public static final CostFunction CROSS_ENTROPY = new CostFunction() {
		@Override
		public Matrix f(Matrix outputZ, Matrix calculatedResult, Matrix expectedResult) {
			return calculatedResult.subtract(expectedResult);
		}
	};

}
