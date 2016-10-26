package mnist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import network.Network;
import math.Matrix;

public class Main {

	public static void main(String[] args) throws IOException {
		
		// Train network on MNIST and test
		
		// Those variables contain the training data as well as the validation data in different indices of the array
		System.out.println("Loading training images");
		Matrix[][] images = null;
		images = MNISTLoader.getImages(new File("res/train-images.idx3-ubyte"), 50000); // Training data
//		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("res/train-images.obj"))){
//			images =  (Matrix[][]) in.readObject();
//			in.close();
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
		
		System.out.println("Loading training labels");
		Matrix[][] labels = null;
		labels = MNISTLoader.getLabels(new File("res/train-labels.idx1-ubyte"), 50000); // Training results
//		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("res/train-labels.obj"))){
//			labels = (Matrix[][]) in.readObject();
//			in.close();
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
		
		System.out.println("Loading test images");
		Matrix[][] testImages = null;
		testImages = MNISTLoader.getImages(new File("res/t10k-images.idx3-ubyte")); // test data
//		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("res/test-images.obj"))){
//			testImages =  (Matrix[][]) in.readObject();
//			in.close();
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
		
		System.out.println("Loading test labels");
		Matrix[][] testLabels = null;
		testLabels = MNISTLoader.getLabels(new File("res/t10k-labels.idx1-ubyte")); // test results
//		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("res/test-labels.obj"))){
//			testLabels =  (Matrix[][]) in.readObject();
//			in.close();
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
		
		// TODO
//		Network n = new Network(new int[] {784, 30, 10});
		
		System.out.println("Starting training process");
		// TODO
//		n.train(images[0], labels[0], 30, 10, 0.3, testImages[0], testLabels[0]);
		System.out.println("Finished training process");
		
	}

}
