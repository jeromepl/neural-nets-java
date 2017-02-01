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
		epochs = 30;
		batchSize = 10;
		learningRate = 0.05;
		regularizationLambda = 0;
		momentumMu = 0;
		costFunction = CostFunction.QUADRATIC;
		activationFunction = ActivationFunction.SIGMOID;
	}

}
