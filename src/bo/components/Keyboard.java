package bo.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import services.Time;

public class Keyboard extends Thread{
	
	//Buffer para entrada de dados do teclado
	private BufferedReader in;
	//Flag para encerrar loop
	private boolean stopKeyboard;
	//Contador de quantidade de loops/instruções realizadas
	private int numberOfLoops;
	
	public Keyboard() {
		super();
		
		//Inicializa variaveis
		this.numberOfLoops = 0;
		this.stopKeyboard = false;
		this.in = new BufferedReader(new InputStreamReader(System.in));
	}

	/*
	 * Ao iniciar a thread, mostra mensagem que inicializou. Logo após começa a ler tudo que é escrito no teclado
	 * e imprime na tela, ao mesmo tempo que conta a quantidade de instruções realizadas, ao final da thread mostra a
	 * quantidade de instruções realizadas no console.
	 */
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
		} finally {
            printOnConsole("Number of Loops: " + this.numberOfLoops);
        }
		
	}
	
	/*
	 * Função para simplificar a escrita da mensagem no console, para seguir um padrão. Sempre mostrar o tempo atual
	 * de execução do programa, seguido do nome do componente e a mensagem.
	 */
	private void printOnConsole(String message){
		System.out.println(Time.getExecutingTime() + " | KEYBOARD  | " + message);
	}
	
	/*
	 * Apenas pula uma linha no console
	 */
	private void skipConsoleLine(){
		System.out.println();
	}
	
	/*
	 * Coloca a flag para encerrar o loop do thread em 'true'
	 */
	public void stopKeyboard() {
		this.stopKeyboard = true;
	}
	
	//Getters and Setters
	public int getNumberOfLoops() {
		return this.numberOfLoops;
	}
	
	
}
