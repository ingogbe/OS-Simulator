package bo.components;

import java.util.concurrent.Semaphore;

import services.Time;

public class Disk extends Thread {
	
	//Contador de quantidade de loops/instru��es realizadas
	private int numberOfLoops;
	//Tamanho do tempo de execu��o/espera entre uma instru��o e outra
	private int loopInterval;
	//Flag para encerrar loop
	private boolean stopDisk;
	//Sem�foro para controle da execu��o das instru��es
	private Semaphore semaphore;
	
	/*
	 * Construtor que apenas inicializa as variaveis e recebe o 'loopInterval' que � o tempo do disco
	 * executar uma instru��o, assim como o semaf�ro do mesmo que � compartilhado com o processador.
	 */
	public Disk(int loopInterval, Semaphore semaphore) {
		super();
		this.numberOfLoops = 0;
		this.loopInterval = loopInterval;
		this.stopDisk = false;
		this.semaphore = semaphore;
	}
	
	/*
	 * Ao inciar a thread, mostra mensagem que incializou. Logo ap�s come�a a execu��o, buscando no semaf�ro
	 * se h� uma instru��o a ser executada atrav�s do m�todo 'acquire()' (equivalente ao m�todo 'down()').
	 * O processador � respons�vel por enviar instru��es ao disco atrav�s do m�todo 'release()' (equivalente ao m�todo 'up()').
	 * Incrementa a quantidade de loops/instru��es executadas, ao final da thread mostra a
	 * quantidade de instru��es realizadas no console.
	 */
	public void run() {
		printOnConsole("Inicia Disco!");
		skipConsoleLine();
		
		try {
			while(!this.stopDisk) {
				this.semaphore.acquire();
				execute();
				this.numberOfLoops++;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
	        printOnConsole("Number of Loops: " + this.numberOfLoops);
	    }
	}
	
	/*
	 * Executa a instru��o "Dei um volta" no tempo de execu��o configurado.
	 */
	private void execute() throws InterruptedException{
		sleep(this.loopInterval);
		
		printOnConsole("Dei uma volta.");
		skipConsoleLine();
		
	}
	
	/*
	 * Fun��o para simplificar a escrita da mensagem no console, para seguir um padr�o. Sempre mostrar o tempo atual
	 * de execu��o do programa, seguido do nome do componente e a mensagem.
	 */
	private void printOnConsole(String message){
		System.out.println(Time.getExecutingTime() + " | DISK      | " + message);
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
	public void stopDisk() {
		this.stopDisk = true;
	}

	//Getters and Setters
	public int getNumberOfLoops() {
		return numberOfLoops;
	}

	public int getLoopInterval() {
		return loopInterval;
	}
	
}
