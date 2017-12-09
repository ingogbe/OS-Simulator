package services;

public class Time {

	private static long initTime = System.currentTimeMillis();
	
	public Time(){
		
	}
	
	public static long getExecutingTime(){
		return (System.currentTimeMillis() - initTime);
	}
}
