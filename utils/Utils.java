package utils;

import java.util.Random;

import math.Matrix;

public class Utils {
	public static void shuffle(Matrix[] a, Matrix[] b) {
		Random r = new Random();
		for (int i = a.length - 1; i > 0; i--) {
			int rVal = r.nextInt(i);
			Matrix tempA = a[i];
			Matrix tempB = b[i];
			a[i] = a[rVal];
			b[i] = b[rVal];
			a[rVal] = tempA;
			b[rVal] = tempB;
		}
	}
}
