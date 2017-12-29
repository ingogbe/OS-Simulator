package view;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import bo.components.Disk;
import bo.components.Keyboard;
import bo.components.Processor;
import bo.data.Data;
import services.Random;
import services.Time;

public class Main {
	
	public static final boolean turnOff = true;
	
	public static Disk d;
	public static Processor p;
	public static Keyboard k;
	
	public static void main(String[] x) {
		/*
		 * Sempre inicializa para salvar o tempo inicial da execução do programar (as mensagens mostradas no console 
		 * utilizam funções estáticas dessa classe, e requerem a inicialização para mostrar os dados corretos)
		 */
		new Time();
		
		//Gera instruções aleatórias na memória (nessa exemplo está gerando entre 1 e 5 instruções aleatórias)
		LinkedList<Data> memory = Random.generateRandomDataList(Random.generateRandomInt(1, 5));
		
		//Imprime todas as instruções (apenas para consulta)
		for(Data d :memory) {
			System.out.println(d);
		}
		System.out.println("\n\n================");
		//*/
		
		//Cria semáforo do disco (disk)
		Semaphore diskSemaphore = new Semaphore(0);
		
		/*
		* Parâmetros do construtor do disco (disck):
		* (inteiro identificador, tempo que disco leva para executar uma instrução, semáforo do disco)
		*/
		d = new Disk(2000, diskSemaphore);
		
		/*
		* Parâmetros do construtor do processador (processor):
		* (inteiro identificador, tempo que processador leva para executar uma instrução, vetor com as instruções, semáforo do disco)
		*/
		p = new Processor(1000, memory, diskSemaphore);
		
		/*
		* Parâmetros do construtor do teclado (keyboard):
		* (inteiro identificador)
		*/
		k = new Keyboard();
		
		//Inicia todos os componentes criados anteriormente
		p.start();
		d.start();
		k.start(); 
		
	}
	
	/*
	 * Caso esteja ativado, para as threads quando não houver mais instruções para o processador executar
	 */
	public static void turnOff(){
		if(turnOff){
			p.stopProcessor();
			k.stopKeyboard();
			d.stopDisk();
		}
	}
}