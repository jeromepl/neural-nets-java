package layers;

import math.Matrix;

public class FullyConnectedLayer extends Layer {
	
	private Matrix weights, biases;
	
	public FullyConnectedLayer(int inputSize, int outputSize){
		weights = new Matrix(inputSize, outputSize);
		biases = new Matrix(outputSize, 1);
	}
	
	public Matrix evaluate(Matrix input) {
		Matrix z = weights.multiply(input);
		z.addSelf(biases);
		return z;
	}
	
}
