package network_efficient;

import math.Function;

public class Network {
	
	private Function activation, cost;
	private Layer[] layers;
	
	
	public Network(Function activation, Function cost, Layer... layers) {
		this.layers = layers;
		this.activation = activation;
		this.cost = cost;
	}
	
	public void train(double[][] trainingData, double[][] trainingResults, int epochs, int batchSize, double learningRate, double regularizationLambda, double momentumMu) {
		train(trainingData, trainingResults, epochs, batchSize, learningRate, regularizationLambda, momentumMu, null, null);
	}
	
	public void train(double[][] trainingData, double[][] trainingResults, int epochs, int batchSize, double learningRate, double regularizationLambda, double momentumMu, double[][] testData, double[][] testResults) {
		
	}
	
	public double[] evaluate(double[] input) {
		for(Layer l : layers){
			input = l.evaluate(input);
			for(int i = 0; i < input.length; i++) {
				input[i] = activation.f(input[i]);
			}
		}
		return input;
	}
	
	
	/* GETTERS AND SETTERS */
	
	public Layer[] getLayers() {
		return layers;
	}
}
