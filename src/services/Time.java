package services;

public class Time {

	private static long initTime = System.currentTimeMillis();
	
	public Time(){
		//Do nothing
	}
	
	//Pega o tempo de execu��o atual do programa
	public static long getExecutingTime(){
		return (System.currentTimeMillis() - initTime);
	}
}
