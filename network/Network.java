package network;

import layers.Layer;
import math.Function;
import math.Matrix;

public class Network {
	
	private Function activation, cost;
	private Layer[] layers;
	
	
	public Network(Function activation, Function cost, Layer... layers) {
		this.layers = layers;
		this.activation = activation;
		this.cost = cost;
	}
	
	public void train(Matrix[] trainingData, Matrix[] trainingResults, int epochs, int batchSize, double learningRate, double regularizationLambda, double momentumMu) {
		train(trainingData, trainingResults, epochs, batchSize, learningRate, regularizationLambda, momentumMu, null, null);
	}
	
	public void train(Matrix[] trainingData, Matrix[] trainingResults, int epochs, int batchSize, double learningRate, double regularizationLambda, double momentumMu, Matrix[] testData, Matrix[] testResults) {
		
	}
	
	public Matrix evaluate(Matrix input) {
		return null;
	}
	
	
	/* GETTERS AND SETTERS */
	
	public Layer[] getLayers() {
		return layers;
	}
}
