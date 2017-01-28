package network;

import java.util.Random;

import math.ActivationFunction;
import math.Matrix;
import utils.Utils;

/**
 * Multilayer Perceptron (MLP)
 * using Sigmoid activation,
 * Quadratic or Cross-entropy cost,
 * Gaussian weight initialization (sharpened),
 * mini-batch momentum-based gradient descent,
 * L2 regularization
 * and back-propagation
 */
// TODO Dropout, log-likelihood cost, tanh activation, ReLU activation, Softmax layer, L1 regularization

public class Network {
	
	private int numLayers;
	private int[] sizes;
	private Matrix[] biases;
	private Matrix[] weights;
	private Matrix[] velocities;
	
	private NetworkConfig config;
	
	public Network(int[] sizes, NetworkConfig config) {
		this.config = config;
		
		this.numLayers = sizes.length;
		this.sizes = sizes;
		this.biases = new Matrix[numLayers - 1]; // No bias for the first layer
		this.weights = new Matrix[numLayers - 1]; // Weights are in-between layers
		this.velocities = new Matrix[numLayers - 1]; // Implementing momentum-based gradient descent
		
		Random r = new Random();
		for (int i = 0; i < numLayers - 1; i++) {
			biases[i] = new Matrix(sizes[i+1], 1);
			weights[i] = new Matrix(sizes[i+1], sizes[i]);
			velocities[i] = new Matrix(sizes[i+1], sizes[i]); // Initialized to 0
			
			for (int j = 0; j < sizes[i+1]; j++) {
				biases[i].setEntry(j, 0, r.nextGaussian());
				
				for (int k = 0; k < sizes[i]; k++) {
					weights[i].setEntry(j, k, r.nextGaussian() / Math.sqrt(sizes[i])); // Network learns faster with a sharper Gaussian distribution
				}
			}
		}
	}
	
	public Matrix evaluate(Matrix a) { // a is the input to the network, a column vector
		for(int i = 0; i < numLayers - 1; i++) {
			Matrix z = weights[i].multiply(a);
			z.addSelf(biases[i]);
			a = z.vectorize(config.activationFunction);
		}
		
		return a;
	}
	
	public void roundUpMax(Matrix m) {
		// Find maximum
		double max = Double.NEGATIVE_INFINITY;
		int[] maxIndex = new int[] {0, 0};
		for (int i = 0; i < m.getNbRows(); i++) {
			for (int j = 0; j < m.getNbColumns(); j++) {
				if (m.getEntry(i, j) > max) {
					max = m.getEntry(i, j);
					m.setEntry(maxIndex[0], maxIndex[1], 0);
					m.setEntry(i, j, 1);
					maxIndex = new int[] {i, j};
				}
				else {
					m.setEntry(i, j, 0);
				}
			}
		}
	}
	
	public void train(Matrix[] trainingData, Matrix[] trainingResults) {
		train(trainingData, trainingResults, new Matrix[]{}, new Matrix[]{});
	}
	
	public void train(Matrix[] trainingData, Matrix[] trainingResults, Matrix[] testData, Matrix[] testResults) {
		
		for (int i = 0; i < config.epochs; i++) { // i the epoch
			
			long startTime = System.nanoTime();
			
			int length = trainingData.length;

			// Shuffle the trainingData
			Utils.shuffle(trainingData, trainingResults);
			
			Matrix[] gradientWeights = new Matrix[numLayers - 1];
			Matrix[] gradientBiases = new Matrix[numLayers - 1];
			
			// Initialize the gradients matrices
			for (int j = 0; j < gradientWeights.length; j++) {
				gradientWeights[j] = new Matrix(weights[j].getNbRows(), weights[j].getNbColumns());
				gradientBiases[j] = new Matrix(biases[j].getNbRows(), biases[j].getNbColumns());
			}
			
			for (int j = 0; j < trainingData.length; j++) { // j is the current training point
				
				Matrix[][] deltaGradients = backpropagate(trainingData[j], trainingResults[j]);

				// Update the gradients for this batch
				for (int k = 0; k < numLayers - 1; k++) {
					gradientWeights[k].addSelf(deltaGradients[0][k]);
					gradientBiases[k].addSelf(deltaGradients[1][k]);
				}
				
				// Check if the batch is done
				if (j > 0 && j % config.batchSize == 0 || j == trainingData.length - 1) {

					for (int k = 0; k < numLayers - 1; k++) {
						
						gradientWeights[k].multiplySelf(config.learningRate);
						velocities[k].multiplySelf(config.momentumMu); // Momentum
						velocities[k].subtractSelf(gradientWeights[k]);
						weights[k].multiplySelf(1 - config.learningRate * config.regularizationLambda / length); // L2 regularization
						weights[k].addSelf(velocities[k]);
						
						gradientBiases[k].multiplySelf(config.learningRate);
						biases[k].subtractSelf(gradientBiases[k]);
						
						// Reset the gradient matrices
						gradientWeights[k].fill(0);
						gradientBiases[k].fill(0);
					}
				}
			}
			
			System.out.println("Epoch " + (i+1) + " completed out of " + config.epochs + " in " + (System.nanoTime() - startTime)/1.0E9 + " seconds");
			int goods = 0;
			for (int j = 0; j < testData.length; j++) {
				Matrix result = evaluate(testData[j]);
				roundUpMax(result);
				if (result.equals(testResults[j]))
					goods++;
			}
			System.out.println("Accuracy: " + (goods * 100.0 / testData.length) + "%");
		}
		
	}
	
	public Matrix[][] backpropagate(Matrix trainingData, Matrix trainingResult) { // returns Matrix[][0] -> weights, [1] is biases
		
		Matrix[] deltaGradientWeights = new Matrix[numLayers - 1];
		Matrix[] deltaGradientBiases = new Matrix[numLayers - 1];
		Matrix[] zs = new Matrix[numLayers - 1];
		Matrix[] activations = new Matrix[numLayers];
		Matrix previousActivation = trainingData;
		
		activations[0] = previousActivation;
		
		// Feed forward layer by layer and store all the z's
		for (int i = 0; i < numLayers - 1; i++) {
			Matrix z = weights[i].multiply(previousActivation);
			z.addSelf(biases[i]);
			zs[i] = z;
			previousActivation = z.vectorize(config.activationFunction);
			activations[i + 1] = previousActivation;
		}
		
		// Move backwards to calculate the errors
		// Error in the final layer (Output error):
		Matrix error = config.costFunction.derivative(activations[activations.length - 1], trainingResult, zs[zs.length - 1], config.activationFunction); // Equation BP1, continued on following line
		deltaGradientBiases[deltaGradientBiases.length - 1] = error;
		deltaGradientWeights[deltaGradientWeights.length - 1] = error.multiplyTransposeM(activations[activations.length - 2]);
		
		for (int i = numLayers - 3; i >= 0; i--) {
			Matrix zsp = zs[i].vectorize(config.activationFunction.derivative());
			error = weights[i + 1].multiplyTransposeSelf(error); // Equation BP2, continued on following line
			error.hadamardProductSelf(zsp);
			
			deltaGradientBiases[i] = error; // Equation BP3
			deltaGradientWeights[i] = error.multiplyTransposeM(activations[i]); // Equation BP4
		}
		
		return new Matrix[][] {deltaGradientWeights, deltaGradientBiases};
	}
	
	public int[] getSizes() {
		return sizes;
	}
	
	public int getNumLayers() {
		return numLayers;
	}
	
	public NetworkConfig getConfig() {
		return config;
	}
	
	public void setConfig(NetworkConfig config) {
		this.config = config;
	}
}
