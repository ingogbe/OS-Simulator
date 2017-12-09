package services;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import bo.data.Data;

public class Random {
	
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
	
	public static String generateRandomComponentInstruction(){
		int i = generateRandomInt(0, Data.componentInstructions.length - 1);
		
		return Data.componentInstructions[i];
	}
	
	public static String generateRandomDataType(){
		int i = generateRandomInt(0, Data.dataTypes.length - 1);
		
		return Data.dataTypes[i];
	}
	
	public static String generateRandomDataTypeWithoutLists(){
		int i = -1;
		
		do {
			i = generateRandomInt(0, Data.dataTypes.length - 1);
		} while(Data.dataTypes[i].equals(Data.DATA_ARRAYLIST));
		
		return Data.dataTypes[i];
	}
	
	public static LinkedList<Data> generateRandomDataList(int size){
		LinkedList<Data> arrayData = new LinkedList<Data>();
		
		while(size > 0){
			arrayData.add(generateRandomData());
			size--;
		}
		
		return arrayData;
	}
	
	public static LinkedList<Data> generateRandomDataListWithoutLists(int size){
		LinkedList<Data> arrayData = new LinkedList<Data>();
		
		while(size > 0){
			arrayData.add(generateRandomDataWithoutLists());
			size--;
		}
		
		return arrayData;
	}
	
	public static Data generateRandomData(){
		
		return new Data(generateRandomDataType());
	}
	
	public static Data generateRandomDataWithoutLists(){
		
		return new Data(generateRandomDataTypeWithoutLists());
	}
}
