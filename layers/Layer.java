package layers;

import math.Matrix;

public abstract class Layer {
	
	public abstract Matrix evaluate(Matrix input);
	
	public abstract Matrix getWeights();
	
	public abstract Matrix getVelocities();
	
}
