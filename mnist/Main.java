package mnist;

import java.io.File;
import java.io.IOException;
import network.Network;
import network.NetworkConfig;
import math.ActivationFunction;
import math.CostFunction;
import math.Matrix;

public class Main {

	public static void main(String[] args) throws IOException {
		
		// Train network on MNIST and test
		
		// Those variables contain the training data as well as the validation data in different indices of the array
		System.out.println("Loading training images");
		Matrix[][] images = null;
		images = MNISTLoader.getImages(new File("res/train-images.idx3-ubyte"), 50000); // Training data
		
		System.out.println("Loading training labels");
		Matrix[][] labels = null;
		labels = MNISTLoader.getLabels(new File("res/train-labels.idx1-ubyte"), 50000); // Training results
		
		System.out.println("Loading test images");
		Matrix[][] testImages = null;
		testImages = MNISTLoader.getImages(new File("res/t10k-images.idx3-ubyte")); // test data
		
		System.out.println("Loading test labels");
		Matrix[][] testLabels = null;
		testLabels = MNISTLoader.getLabels(new File("res/t10k-labels.idx1-ubyte")); // test results
		
		
		NetworkConfig config1 = new NetworkConfig();
		config1.epochs = 20;
		config1.batchSize = 10;
		config1.learningRate = 0.05;
		config1.regularizationLambda = 5.0;
		config1.momentumMu = 0.3;
		config1.costFunction = CostFunction.CROSS_ENTROPY;
		config1.activationFunction = ActivationFunction.SIGMOID;
		
		NetworkConfig config2 = new NetworkConfig();
		config2.epochs = 30;
		config2.batchSize = 10;
		config2.learningRate = 0.05;
		config2.regularizationLambda = 0;
		config2.momentumMu = 0;
		config2.costFunction = CostFunction.QUADRATIC;
		config2.activationFunction = ActivationFunction.SIGMOID;
		
		Network n = new Network(new int[] {784, 100, 10}, config1);
		
		System.out.println("Starting training process");
		
		// TODO use validation data (images[1] & labels[1])
		n.train(images[0], labels[0], testImages[0], testLabels[0]);
		
		System.out.println("Finished training process");
		
	}

}
