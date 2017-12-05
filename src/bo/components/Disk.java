package bo.components;

public class Disk extends Thread {
	
	private int numberOfLoops;
	private int loopInterval;
	private boolean stopDisk;
	private int identifier;
	
	public Disk(int identifier, int loopInterval) {
		super();
		this.identifier = identifier;
		this.numberOfLoops = 0;
		this.loopInterval = loopInterval;
		this.stopDisk = false;
	}

	public void run() {
		System.out.println("Inicia Disco!");
		
		while(!this.stopDisk) {
			System.out.println("Dei uma volta.");
			
			try {
				sleep(getLoopInterval());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			this.numberOfLoops++;
		}
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
