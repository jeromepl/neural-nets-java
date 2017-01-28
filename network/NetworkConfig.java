package network;

import math.ActivationFunction;
import math.CostFunction;

public class NetworkConfig {
	
	public int epochs;
	public int batchSize;
	public double learningRate;
	public double regularizationLambda;
	public double momentumMu;
	public CostFunction costFunction;
	public ActivationFunction activationFunction;
	
	public NetworkConfig() {
		epochs = 20;
		batchSize = 10;
		learningRate = 0.05;
		regularizationLambda = 5.0;
		momentumMu = 0.3;
		costFunction = CostFunction.CROSS_ENTROPY;
		activationFunction = ActivationFunction.SIGMOID;
	}

}
