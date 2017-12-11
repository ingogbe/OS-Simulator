package bo.components;

import java.util.concurrent.Semaphore;

import services.Time;

public class Disk extends Thread {
	
	//Contador de quantidade de loops/instruções realizadas
	private int numberOfLoops;
	//Tamanho do tempo de execução/espera entre uma instrução e outra
	private int loopInterval;
	//Flag para encerrar loop
	private boolean stopDisk;
	//Semáforo para controle da execução das instruções
	private Semaphore semaphore;
	
	/*
	 * Construtor que apenas inicializa as variaveis e recebe o 'loopInterval' que é o tempo do disco
	 * executar uma instrução, assim como o semafóro do mesmo que é compartilhado com o processador.
	 */
	public Disk(int loopInterval, Semaphore semaphore) {
		super();
		this.numberOfLoops = 0;
		this.loopInterval = loopInterval;
		this.stopDisk = false;
		this.semaphore = semaphore;
	}
	
	/*
	 * Ao inciar a thread, mostra mensagem que incializou. Logo após começa a execução, buscando no semafóro
	 * se há uma instrução a ser executada através do método 'acquire()' (equivalente ao método 'down()').
	 * O processador é responsável por enviar instruções ao disco através do método 'release()' (equivalente ao método 'up()').
	 * Incrementa a quantidade de loops/instruções executadas, ao final da thread mostra a
	 * quantidade de instruções realizadas no console.
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
	 * Executa a instrução "Dei um volta" no tempo de execução configurado.
	 */
	private void execute() throws InterruptedException{
		sleep(this.loopInterval);
		
		printOnConsole("Dei uma volta.");
		skipConsoleLine();
		
	}
	
	/*
	 * Função para simplificar a escrita da mensagem no console, para seguir um padrão. Sempre mostrar o tempo atual
	 * de execução do programa, seguido do nome do componente e a mensagem.
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
