package bo.components;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import bo.data.Data;
import services.Time;
import view.Main;

public class Processor extends Thread {
	
	//Contador de quantidade de loops/instruções realizadas
	private int numberOfLoops;
	//Tamanho do tempo de execução/espera entre uma instrução e outra
	private int loopInterval;
	//Flag para encerrar loop
	private boolean stopProcessor;
	//Lista/Vetor com as instruções a serem executadas pelo processador
	private LinkedList<Data> memory;
	//Semáforo para controle da execução das instruções do disco
	private Semaphore diskSemaphore;
	
	//Contem a instrução que está sendo executada no momento
	private Data programCounter;
	
	/*
	 * Construtor que apenas inicializa as variaveis e recebe o 'loopInterval' que é o tempo do processador
	 * executar uma instrução, o semafóro compartilhado para controle do disco, e também o lista/vetor com as
	 * intruções a serem executadas pelo processador.
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
	 * Ao inciar a thread, mostra mensagem que incializou. Executa as instruções da lista/vetor no estilo FIFO (First in, First out)
	 * até que não possua mais intruções a serem executadas.
	 * Realiza a chamada aos componentes pertinentes de acordo com a instrução, assim como executa vetores de instruções ou instruções
	 * simples (Integer)
	 * 
	 * Passo a passo descrito dentro da função
	 */
	public void run() {
		printOnConsole("Inicia Processador!");
		skipConsoleLine();
		
		try{
			while(!this.stopProcessor) {
				sleep(getLoopInterval());
				
				//Verifica se não possui nenhuma instrução para procurar uma na memória; ou se ela não é do tipo vetor (pois o
				//jeito de executar do vetor é diferente da instrução comum dentro da memória)
				if(this.programCounter == null || !this.programCounter.getIdentifier().equals(Data.DATA_ARRAYLIST)){
					//Se existe instruções na memória, pega a primeira e manda para execução
					if(this.memory.size() > 0){
						this.programCounter = this.memory.removeFirst();
						executeData(this.programCounter);
					}
					//Senão mostra no console que não possui mais instruções
					else{
						printOnConsole("Não há mais instruções");
						skipConsoleLine();
						Main.turnOff();
						break;
					}
				}
				//Caso a instrução atual for uma lista/vetor executa este trecho de código
				else if(this.programCounter.getIdentifier().equals(Data.DATA_ARRAYLIST)){
					//Se existe instruções nesse vetor, pega a primeira e manda para execução
					if(this.programCounter.getDataList().size() > 0){
						executeData(this.programCounter.getDataList().removeFirst());
					}
					else{
						//Caso não exista, busca próxima instrução na memória, e envia para execução
						if(this.memory.size() > 0){
							this.programCounter = this.memory.removeFirst();
							executeData(this.programCounter);
						}
						//Senão mostra no console que não possui mais instruções
						else{							
							printOnConsole("Não há mais instruções");
							skipConsoleLine();
							Main.turnOff();
							break;
						}
					}
				}
				else{
					//Caso entrar neste if quer dizer que o PC não está definido ou registrado corretamente
					printOnConsole("Registrador PC não definido");
					skipConsoleLine();
					Main.turnOff();
					break;
				}
				
				//Incrementa a quantidade de instruções executadas
				this.numberOfLoops++;
				
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
	        printOnConsole("Number of Loops: " + this.numberOfLoops);
	    }
		
		
	}
	
	/*
	 * Executa a instrução passada. Fazendo as chamadas corretas para cada tipo de instrução.
	 * 
	 * Passo a passo descrito dentro da função
	 */
	public void executeData(Data data) throws InterruptedException{
		//Se for uma instrução do tipo componente, faz a chamada pertinente
		if(data.getIdentifier().equals(Data.DATA_COMPONENT_INSTRUCTION)){
			//Caso for disco, incremente o semáforo usando a função 'realease()' (equivalente a função 'up()')
			//E imprime no console que executou a instrução
			if(data.getDataComponentInstruction().equals(Data.COMPONENT_INSTRUCTION_DISK)){
				printOnConsole("Executei uma instrução.");
				printOnConsole(data.toString());
				skipConsoleLine();
				
				this.diskSemaphore.release();
			}
			//Componente que não foi configurado	
			else{		
				printOnConsole("Executei uma instrução.");
				printOnConsole("Componente não configurado: " + data.getDataComponentInstruction());
				skipConsoleLine();
			}
		}
		//Se enviou um array para execução já começa executar a primeira instrução do mesmo
		else if(data.getIdentifier().equals(Data.DATA_ARRAYLIST)){
			executeData(this.programCounter.getDataList().removeFirst());
		}
		//Executa valor. Instrução simples. Apenas mostra no console
		else if(data.getIdentifier().equals(Data.DATA_INTEGER)){
			printOnConsole("Executei uma instrução.");
			printOnConsole(data.toString());
			skipConsoleLine();
		}
		//Instrução não reconhecida
		else{
			printOnConsole("Executei uma instrução.");
			printOnConsole("Instrução não reconhecida: " + data.getIdentifier());
			skipConsoleLine();
		}
		
	}
	
	/*
	 * Função para simplificar a escrita da mensagem no console, para seguir um padrão. Sempre mostrar o tempo atual
	 * de execução do programa, seguido do nome do componente e a mensagem.
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
