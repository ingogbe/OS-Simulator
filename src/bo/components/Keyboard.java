package bo.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import services.Time;

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
		printOnConsole("Inicia Teclado!");
		skipConsoleLine();
		
		String line = "";
		
		try {
			while(!this.stopKeyboard) {
				line = in.readLine();
				printOnConsole("Usuário digitou algo: '" + line + "'.");
				skipConsoleLine();
				this.numberOfLoops++;
			}
			
			this.in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void printOnConsole(String message){
		System.out.println(Time.getExecutingTime() + " | KEYBOARD  | " + message);
	}
	
	private void skipConsoleLine(){
		System.out.println();
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
