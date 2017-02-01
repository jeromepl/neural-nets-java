package layers;

import java.util.Random;

import math.Matrix;

public class FullyConnectedLayer extends Layer {
	
	private Matrix weights, velocities, biases;
	
	public FullyConnectedLayer(int inputSize, int outputSize){
		weights = new Matrix(outputSize, inputSize);
		biases = new Matrix(outputSize, 1);
		velocities = new Matrix(outputSize, inputSize); // Initialized to 0
		
		// Initialize the weights and biases
		Random r = new Random();
			
		for (int i = 0; i < outputSize; i++) {
			biases.setEntry(i, 0, r.nextGaussian());
			
			for (int j = 0; j < inputSize; j++) {
				weights.setEntry(i, j, r.nextGaussian() / Math.sqrt(inputSize));
			}
		}
	}
	
	public Matrix evaluate(Matrix input) {
		Matrix z = weights.multiply(input);
		z.addSelf(biases);
		return z;
	}
	
	public Matrix getWeights() {
		return weights;
	}
	
	public Matrix getVelocities() {
		return velocities;
	}
	
}
