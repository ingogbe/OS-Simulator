package bo.components;

import java.util.concurrent.Semaphore;

import services.Time;

public class Disk extends Thread {
	
	private int numberOfLoops;
	private int loopInterval;
	private boolean stopDisk;
	private int identifier;
	private Semaphore semaphore;
	
	public Disk(int identifier, int loopInterval, Semaphore semaphore) {
		super();
		this.identifier = identifier;
		this.numberOfLoops = 0;
		this.loopInterval = loopInterval;
		this.stopDisk = false;
		this.semaphore = semaphore;
	}

	public void run() {
		printOnConsole("Inicia Disco!");
		skipConsoleLine();
		
		while(!this.stopDisk) {
			try {
				this.semaphore.acquire();
				execute();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            
		}
	}
	
	private void execute() throws InterruptedException{
		sleep(this.loopInterval);
		
		printOnConsole("Dei uma volta.");
		skipConsoleLine();
		
		this.numberOfLoops++;
	}
	
	private void printOnConsole(String message){
		System.out.println(Time.getExecutingTime() + " | DISK      | " + message);
	}
	
	private void skipConsoleLine(){
		System.out.println();
	}
	
	public void stopDisk() {
		this.stopDisk = true;
	}

	public int getNumberOfLoops() {
		return numberOfLoops;
	}

	public int getLoopInterval() {
		return loopInterval;
	}
	
	public int getIdentifier() {
		return this.identifier;
	}
	
}
