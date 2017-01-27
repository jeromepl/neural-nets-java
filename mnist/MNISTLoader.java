package mnist;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import math.Matrix;

public class MNISTLoader {
	
	public static final String[] LABELS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
	public static Matrix[][] getImages(File file, int... divisions) throws IOException {
		Matrix[][] images;
		DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
		
		input.readInt(); //Skip the "magic number"
		int n = input.readInt();
		int row = input.readInt();
		int col = input.readInt();
		
		images = new Matrix[divisions.length + 1][];
		int imageCount = 0;
		for (int i = 0; i < divisions.length; i++) {
			images[i] = new Matrix[divisions[i]];
			imageCount += divisions[i];
		}
		// Leave space for the remaining images
		images[images.length - 1] = new Matrix[n - imageCount];
		
		int divisionIndex = 0;
		int imageIndex = 0; // The index in the current division
		
		for(int i = 0; i < n; i++) {
			Matrix image = new Matrix(row*col, 1);
			for(int j = 0; j < row * col; j++) {
				image.setEntry(j, 0, input.readUnsignedByte() / 255d);
			}
			
			if (imageIndex >= images[divisionIndex].length) {
				imageIndex = 0;
				divisionIndex++;
			}
			images[divisionIndex][imageIndex] = image;
			imageIndex++;
		}
		
		input.close();
		return images; // images[imageIndex][pixelIndex], pixels were flattened into a 1D array
	}
	
	public static Matrix[][] getLabels(File file, int... divisions) throws IOException {
		Matrix[][] labels;
		DataInputStream input = new DataInputStream(new FileInputStream(file));
		
		input.readInt(); //Skip the "magic number"
		int n = input.readInt();
		
		labels = new Matrix[divisions.length + 1][];
		int labelCount = 0;
		for (int i = 0; i < divisions.length; i++) {
			labels[i] = new Matrix[divisions[i]];
			labelCount += divisions[i];
		}
		// Leave space for the remaining images
		labels[labels.length - 1] = new Matrix[n - labelCount];
		
		int divisionIndex = 0;
		int labelIndex = 0; // The index in the current division
		
		for(int i = 0; i < n; i++) {
			Matrix label = new Matrix(LABELS.length, 1);
			String labelString = "" + input.readUnsignedByte(); //TODO optimize by removing String dependency
			for (int j = 0; j < LABELS.length; j++) {
				if (LABELS[j].equals(labelString))
					label.setEntry(j, 0, 1);
			}
			
			if (labelIndex >= labels[divisionIndex].length) {
				labelIndex = 0;
				divisionIndex++;
			}
			
			labels[divisionIndex][labelIndex] = label;
			labelIndex++;
		}
		
		input.close();
		return labels;
	}
	
	
	public static double[][] getImagesArray(File file, int... divisions) throws IOException {
		Matrix[][] matrices = getImages(file, divisions);
		double[][] images = new double[matrices.length][];
		for(int i=0; i<images.length; i++){
			images[i] = matrices[i][0].toFlatArray();
		}
		return images;
	}
}
