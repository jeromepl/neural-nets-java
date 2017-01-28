package math;

// Derivatives of cost functions
public abstract class CostFunction {
	
	public static final CostFunction QUADRATIC = new CostFunction() {
		@Override
		public Matrix derivative(Matrix calculatedResult, Matrix expectedResult, Matrix outputZ, ActivationFunction activationFunction) {
			Matrix cost = calculatedResult.subtract(expectedResult);
			cost.hadamardProductSelf(outputZ.vectorize(activationFunction.derivative()));
			return cost;
		}
	};
	
	public static final CostFunction CROSS_ENTROPY = new CostFunction() {
		@Override
		public Matrix derivative(Matrix calculatedResult, Matrix expectedResult, Matrix outputZ, ActivationFunction activationFunction) {
			return calculatedResult.subtract(expectedResult);
		}
	};
	
	public abstract Matrix derivative(Matrix calculatedResult, Matrix expectedResult, Matrix outputZ, ActivationFunction activationFunction);

}
