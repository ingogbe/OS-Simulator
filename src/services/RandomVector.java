package services;

import java.util.concurrent.ThreadLocalRandom;

public class RandomVector {
	
	public static int generateRandomInt(int rangeMin, int rangeMax) {
		return ThreadLocalRandom.current().nextInt(rangeMin, rangeMax + 1);
	}
	
	public static int[] generateRandomIntVector(int size, int rangeMin, int rangeMax) {
		
		int vector[] = new int[size];
		
		for (int i = 0; i < vector.length; i++) {
			vector[i] = generateRandomInt(rangeMin, rangeMax);
		}
		
		return vector;
	}
}
