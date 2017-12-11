package bo.components;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import bo.data.Data;
import services.Time;
import view.Main;

public class Processor extends Thread {
	
	//Contador de quantidade de loops/instru��es realizadas
	private int numberOfLoops;
	//Tamanho do tempo de execu��o/espera entre uma instru��o e outra
	private int loopInterval;
	//Flag para encerrar loop
	private boolean stopProcessor;
	//Lista/Vetor com as instru��es a serem executadas pelo processador
	private LinkedList<Data> memory;
	//Sem�foro para controle da execu��o das instru��es do disco
	private Semaphore diskSemaphore;
	
	//Contem a instru��o que est� sendo executada no momento
	private Data programCounter;
	
	/*
	 * Construtor que apenas inicializa as variaveis e recebe o 'loopInterval' que � o tempo do processador
	 * executar uma instru��o, o semaf�ro compartilhado para controle do disco, e tamb�m o lista/vetor com as
	 * intru��es a serem executadas pelo processador.
	 */
	public Processor(int loopInterval, LinkedList<Data> memory, Semaphore diskSemaphore) {
		super();
		this.numberOfLoops = 0;
		this.loopInterval = loopInterval;
		this.stopProcessor = false;
		this.programCounter = null;
		this.memory = memory;
		this.diskSemaphore = diskSemaphore;
	}

	/*
	 * Ao inciar a thread, mostra mensagem que incializou. Executa as instru��es da lista/vetor no estilo FIFO (First in, First out)
	 * at� que n�o possua mais intru��es a serem executadas.
	 * Realiza a chamada aos componentes pertinentes de acordo com a instru��o, assim como executa vetores de instru��es ou instru��es
	 * simples (Integer)
	 * 
	 * Passo a passo descrito dentro da fun��o
	 */
	public void run() {
		printOnConsole("Inicia Processador!");
		skipConsoleLine();
		
		try{
			while(!this.stopProcessor) {
				sleep(getLoopInterval());
				
				//Verifica se n�o possui nenhuma instru��o para procurar uma na mem�ria; ou se ela n�o � do tipo vetor (pois o
				//jeito de executar do vetor � diferente da instru��o comum dentro da mem�ria)
				if(this.programCounter == null || !this.programCounter.getIdentifier().equals(Data.DATA_ARRAYLIST)){
					//Se existe instru��es na mem�ria, pega a primeira e manda para execu��o
					if(this.memory.size() > 0){
						this.programCounter = this.memory.removeFirst();
						executeData(this.programCounter);
					}
					//Sen�o mostra no console que n�o possui mais instru��es
					else{
						printOnConsole("N�o h� mais instru��es");
						skipConsoleLine();
						Main.turnOff();
						break;
					}
				}
				//Caso a instru��o atual for uma lista/vetor executa este trecho de c�digo
				else if(this.programCounter.getIdentifier().equals(Data.DATA_ARRAYLIST)){
					//Se existe instru��es nesse vetor, pega a primeira e manda para execu��o
					if(this.programCounter.getDataList().size() > 0){
						executeData(this.programCounter.getDataList().removeFirst());
					}
					else{
						//Caso n�o exista, busca pr�xima instru��o na mem�ria, e envia para execu��o
						if(this.memory.size() > 0){
							this.programCounter = this.memory.removeFirst();
							executeData(this.programCounter);
						}
						//Sen�o mostra no console que n�o possui mais instru��es
						else{							
							printOnConsole("N�o h� mais instru��es");
							skipConsoleLine();
							Main.turnOff();
							break;
						}
					}
				}
				else{
					//Caso entrar neste if quer dizer que o PC n�o est� definido ou registrado corretamente
					printOnConsole("Registrador PC n�o definido");
					skipConsoleLine();
					Main.turnOff();
					break;
				}
				
				//Incrementa a quantidade de instru��es executadas
				this.numberOfLoops++;
				
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
	        printOnConsole("Number of Loops: " + this.numberOfLoops);
	    }
		
		
	}
	
	/*
	 * Executa a instru��o passada. Fazendo as chamadas corretas para cada tipo de instru��o.
	 * 
	 * Passo a passo descrito dentro da fun��o
	 */
	public void executeData(Data data) throws InterruptedException{
		//Se for uma instru��o do tipo componente, faz a chamada pertinente
		if(data.getIdentifier().equals(Data.DATA_COMPONENT_INSTRUCTION)){
			//Caso for disco, incremente o sem�foro usando a fun��o 'realease()' (equivalente a fun��o 'up()')
			//E imprime no console que executou a instru��o
			if(data.getDataComponentInstruction().equals(Data.COMPONENT_INSTRUCTION_DISK)){
				printOnConsole("Executei uma instru��o.");
				printOnConsole(data.toString());
				skipConsoleLine();
				
				this.diskSemaphore.release();
			}
			//Componente que n�o foi configurado	
			else{		
				printOnConsole("Executei uma instru��o.");
				printOnConsole("Componente n�o configurado: " + data.getDataComponentInstruction());
				skipConsoleLine();
			}
		}
		//Se enviou um array para execu��o j� come�a executar a primeira instru��o do mesmo
		else if(data.getIdentifier().equals(Data.DATA_ARRAYLIST)){
			executeData(this.programCounter.getDataList().removeFirst());
		}
		//Executa valor. Instru��o simples. Apenas mostra no console
		else if(data.getIdentifier().equals(Data.DATA_INTEGER)){
			printOnConsole("Executei uma instru��o.");
			printOnConsole(data.toString());
			skipConsoleLine();
		}
		//Instru��o n�o reconhecida
		else{
			printOnConsole("Executei uma instru��o.");
			printOnConsole("Instru��o n�o reconhecida: " + data.getIdentifier());
			skipConsoleLine();
		}
		
	}
	
	/*
	 * Fun��o para simplificar a escrita da mensagem no console, para seguir um padr�o. Sempre mostrar o tempo atual
	 * de execu��o do programa, seguido do nome do componente e a mensagem.
	 */
	private void printOnConsole(String message){
		System.out.println(Time.getExecutingTime() + " | PROCESSOR | " + message);
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
	public void stopProcessor() {
		this.stopProcessor = true;
	}

	//Getters and Setters
	public int getNumberOfLoops() {
		return this.numberOfLoops;
	}

	public int getLoopInterval() {
		return this.loopInterval;
	}
	
	public LinkedList<Data> getMemory(){
		return this.memory;
	}
	
}
