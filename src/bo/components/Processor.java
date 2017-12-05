package bo.components;

public class Processor extends Thread {
	
	private int numberOfLoops;
	private int loopInterval;
	private boolean stopProcessor;
	private int identifier;
	
	private int programCounter;
	
	public Processor(int identifier, int loopInterval) {
		super();
		this.identifier = identifier;
		this.numberOfLoops = 0;
		this.loopInterval = loopInterval;
		this.stopProcessor = false;
		this.programCounter = 0;
	}

	public void run() {
		System.out.println("Inicia Processador!");
		
		while(!this.stopProcessor) {
			System.out.println("Executei uma instrução.");
			
			try {
				sleep(getLoopInterval());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			this.numberOfLoops++;
		}
	}
	
	public void stopProcessor() {
		this.stopProcessor = true;
	}

	public int getNumberOfLoops() {
		return this.numberOfLoops;
	}

	public int getLoopInterval() {
		return this.loopInterval;
	}
	
	public int getIdentifier() {
		return this.identifier;
	}
	
}
