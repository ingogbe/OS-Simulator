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
	
	public static final boolean turnOff = false;
	
	public static Disk d;
	public static Processor p;
	public static Keyboard k;
	
	public static void main(String[] x) {
		
		new Time();
		
		LinkedList<Data> memory = Random.generateRandomDataList(Random.generateRandomInt(1, 5));
		for(Data d :memory) {
			System.out.println(d);
		}
		System.out.println("\n\n================");
		
		Semaphore diskSemaphore = new Semaphore(0);
		
		d = new Disk(2, 2000, diskSemaphore);
		p = new Processor(1, 1000, memory, diskSemaphore);
		k = new Keyboard(3);
		
		p.start();
		d.start();
		k.start(); 
		
	}
	
	public static void turnOff(){
		if(turnOff){
			p.stopProcessor();
			k.stopKeyboard();
			d.stopDisk();
		}
	}
}