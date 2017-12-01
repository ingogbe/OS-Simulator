package bo.components;

public class Keyboard extends Thread {

	public static final int EXECUTE_INFINITE = -1;
	
	private int numberOfLoops;
	
	public Keyboard(int numberOfLoops) {
		super();
		this.numberOfLoops = numberOfLoops;
	}

	public void run() {
		System.out.println("Inicia Keyboard!");
		
		while(getNumberOfLoops() == EXECUTE_INFINITE || getNumberOfLoops() > 0) {
			
		}
	}

	public int getNumberOfLoops() {
		return numberOfLoops;
	}

	public void setNumberOfLoops(int numberOfLoops) {
		this.numberOfLoops = numberOfLoops;
	}
	
}
