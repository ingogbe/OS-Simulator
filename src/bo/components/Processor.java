package bo.components;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import bo.data.Data;
import services.Time;
import view.Main;

public class Processor extends Thread {
	
	private int numberOfLoops;
	private int loopInterval;
	private boolean stopProcessor;
	private int identifier;
	private LinkedList<Data> memory;
	private Semaphore diskSemaphore;
	
	private Data programCounter;
	
	public Processor(int identifier, int loopInterval, LinkedList<Data> memory, Semaphore diskSemaphore) {
		super();
		this.identifier = identifier;
		this.numberOfLoops = 0;
		this.loopInterval = loopInterval;
		this.stopProcessor = false;
		this.programCounter = null;
		this.memory = memory;
		this.diskSemaphore = diskSemaphore;
	}

	public void run() {
		printOnConsole("Inicia Processador!");
		skipConsoleLine();
		
		while(!this.stopProcessor) {
			
			try{
				
				sleep(getLoopInterval());
				
				if(this.programCounter == null || !this.programCounter.getIdentifier().equals(Data.DATA_ARRAYLIST)){
					if(this.memory.size() > 0){
						this.programCounter = this.memory.removeFirst();
						executeData(this.programCounter);
					}
					else{
						printOnConsole("N�o h� mais instru��es");
						skipConsoleLine();
						Main.turnOff();
						break;
					}
				}
				else if(this.programCounter.getIdentifier().equals(Data.DATA_ARRAYLIST)){
					if(this.programCounter.getDataList().size() > 0){
						executeData(this.programCounter.getDataList().removeFirst());
					}
					else{
						if(this.memory.size() > 0){
							this.programCounter = this.memory.removeFirst();
							executeData(this.programCounter);
						}
						else{							
							printOnConsole("N�o h� mais instru��es");
							skipConsoleLine();
							Main.turnOff();
							break;
						}
					}
				}
				else{
					printOnConsole("Registrador PC n�o definido");
					skipConsoleLine();
					Main.turnOff();
					break;
				}
				
				this.numberOfLoops++;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	public void executeData(Data data) throws InterruptedException{
		
		
		if(data.getIdentifier().equals(Data.DATA_COMPONENT_INSTRUCTION)){
			if(data.getDataComponentInstruction().equals(Data.COMPONENT_INSTRUCTION_DISK)){
				printOnConsole("Executei uma instru��o.");
				printOnConsole(data.toString());
				skipConsoleLine();
				
				this.diskSemaphore.release();
			}
			else{
				//Componente que n�o foi configurado				
				printOnConsole("Executei uma instru��o.");
				printOnConsole("Componente n�o configurado: " + data.getDataComponentInstruction());
				skipConsoleLine();
			}
		}
		else if(data.getIdentifier().equals(Data.DATA_ARRAYLIST)){
			//Se enviou um array para execu��o j� come�a executar a primeira instru��o do mesmo
			executeData(this.programCounter.getDataList().removeFirst());
		}
		else if(data.getIdentifier().equals(Data.DATA_INTEGER)){
			//Executa valor
			printOnConsole("Executei uma instru��o.");
			printOnConsole(data.toString());
			skipConsoleLine();
		}
		else{
			printOnConsole("Executei uma instru��o.");
			printOnConsole("Instru��o n�o reconhecida: " + data.getIdentifier());
			skipConsoleLine();
		}
		
	}
	
	private void printOnConsole(String message){
		System.out.println(Time.getExecutingTime() + " | PROCESSOR | " + message);
	}
	
	private void skipConsoleLine(){
		System.out.println();
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
	
	public LinkedList<Data> getMemory(){
		return this.memory;
	}
	
}
