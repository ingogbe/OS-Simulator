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
		 * Sempre inicializa para salvar o tempo inicial da execu��o do programar (as mensagens mostradas no console 
		 * utilizam fun��es est�ticas dessa classe, e requerem a inicializa��o para mostrar os dados corretos)
		 */
		new Time();
		
		//Gera instru��es aleat�rias na mem�ria (nessa exemplo est� gerando entre 1 e 5 instru��es aleat�rias)
		LinkedList<Data> memory = Random.generateRandomDataList(Random.generateRandomInt(1, 5));
		
		//Imprime todas as instru��es (apenas para consulta)
		for(Data d :memory) {
			System.out.println(d);
		}
		System.out.println("\n\n================");
		//*/
		
		//Cria sem�foro do disco (disk)
		Semaphore diskSemaphore = new Semaphore(0);
		
		/*
		* Par�metros do construtor do disco (disck):
		* (inteiro identificador, tempo que disco leva para executar uma instru��o, sem�foro do disco)
		*/
		d = new Disk(2000, diskSemaphore);
		
		/*
		* Par�metros do construtor do processador (processor):
		* (inteiro identificador, tempo que processador leva para executar uma instru��o, vetor com as instru��es, sem�foro do disco)
		*/
		p = new Processor(1000, memory, diskSemaphore);
		
		/*
		* Par�metros do construtor do teclado (keyboard):
		* (inteiro identificador)
		*/
		k = new Keyboard();
		
		//Inicia todos os componentes criados anteriormente
		p.start();
		d.start();
		k.start(); 
		
	}
	
	/*
	 * Caso esteja ativado, para as threads quando n�o houver mais instru��es para o processador executar
	 */
	public static void turnOff(){
		if(turnOff){
			p.stopProcessor();
			k.stopKeyboard();
			d.stopDisk();
		}
	}
}