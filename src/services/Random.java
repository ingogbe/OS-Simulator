package services;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import bo.data.Data;

public class Random {
	
	/*
	 * Gera um número inteiro aleatório dentro da faixa de valor passada
	 */
	public static int generateRandomInt(int rangeMin, int rangeMax) {
		return ThreadLocalRandom.current().nextInt(rangeMin, rangeMax + 1);
	}
	
	/*
	 * Gera um vetor de números inteiros de tamanho 'size', dentro da faixa de valor passada
	 */
	public static int[] generateRandomIntVector(int size, int rangeMin, int rangeMax) {
		
		int vector[] = new int[size];
		
		for (int i = 0; i < vector.length; i++) {
			vector[i] = generateRandomInt(rangeMin, rangeMax);
		}
		
		return vector;
	}
	
	/*
	 * Gera um tipo de instrução de componente aleatório, de acordo com os componentes disponiveis
	 * Disk, etc...
	 */
	public static String generateRandomComponentInstruction(){
		int i = generateRandomInt(0, Data.componentInstructions.length - 1);
		
		return Data.componentInstructions[i];
	}
	
	/*
	 * Gera um tipo de instrução aleatorio de acordo com as disponiveis
	 * Component Instruction, Integer, ArrayList, etc...
	 */
	public static String generateRandomDataType(){
		int i = generateRandomInt(0, Data.dataTypes.length - 1);
		
		return Data.dataTypes[i];
	}
	
	/*
	 * Gera um tipo de instrução aleatorio de acordo com as disponiveis excluindo o tipo ArrayList
	 * Component Instruction, Integer, etc...
	 */
	public static String generateRandomDataTypeWithoutLists(){
		int i = -1;
		
		do {
			i = generateRandomInt(0, Data.dataTypes.length - 1);
		} while(Data.dataTypes[i].equals(Data.DATA_ARRAYLIST));
		
		return Data.dataTypes[i];
	}
	
	
	/*
	 * Gera um vetor de instruções aleatórias de acordo com as disponiveis
	 * Component Instruction, Integer, ArrayList, etc...
	 */
	public static LinkedList<Data> generateRandomDataList(int size){
		LinkedList<Data> arrayData = new LinkedList<Data>();
		
		while(size > 0){
			arrayData.add(generateRandomData());
			size--;
		}
		
		return arrayData;
	}
	
	/*
	 * Gera um vetor de instruções aleatórias de acordo com as disponiveis excluindo o tipo ArrayList
	 * Component Instruction, Integer, etc...
	 */
	public static LinkedList<Data> generateRandomDataListWithoutLists(int size){
		LinkedList<Data> arrayData = new LinkedList<Data>();
		
		while(size > 0){
			arrayData.add(generateRandomDataWithoutLists());
			size--;
		}
		
		return arrayData;
	}
	
	/*
	 * Gera uma instrução aleatoria de acordo com as disponiveis
	 * Component Instruction, Integer, ArrayList, etc...
	 */
	public static Data generateRandomData(){
		
		return new Data(generateRandomDataType());
	}
	
	
	/*
	 * Gera uma instrução aleatoria de acordo com as disponiveis excluindo o tipo ArrayList
	 * Component Instruction, Integer, etc...
	 */
	public static Data generateRandomDataWithoutLists(){
		
		return new Data(generateRandomDataTypeWithoutLists());
	}
}
