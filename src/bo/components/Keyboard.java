package bo.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Keyboard extends Thread{
	
	private BufferedReader in;
	private boolean stopKeyboard;
	private int numberOfLoops;
	private int identifier;
	
	public Keyboard(int identifier) {
		super();
		
		this.identifier = identifier;
		this.numberOfLoops = 0;
		this.stopKeyboard = false;
		this.in = new BufferedReader(new InputStreamReader(System.in));
	}

	public void run() {
		System.out.println("Inicia Teclado!");
		String line = "";
		
		try {
			while(!this.stopKeyboard) {
				line = in.readLine();
				System.out.println("Usuário digitou algo: '" + line + "'.");
				this.numberOfLoops++;
			}
			
			this.in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void stopKeyboard() {
		this.stopKeyboard = true;
	}
	
	public int getNumberOfLoops() {
		return this.numberOfLoops;
	}

	public int getIdentifier() {
		return this.identifier;
	}
	
	
}
