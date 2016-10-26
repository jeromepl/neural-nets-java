package network_efficient;

public abstract class Layer {
	
	public abstract double[] evaluate(double[] input);
	
	public abstract double[] backPropagate(double[] output, double[] previousError);
	
}
