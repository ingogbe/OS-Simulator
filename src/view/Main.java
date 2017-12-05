package view;

import bo.components.Disk;
import bo.components.Keyboard;
import bo.components.Processor;
import services.RandomVector;

public class Main {
	public static void main(String[] x) {
		
		int memory[] = RandomVector.generateRandomIntVector(20, 1, 10);
		
		for(int m :memory) {
			System.out.print(m + " ");
		}
		System.out.println();
		
		Processor p = new Processor(1, 1000);
		Disk d = new Disk(2, 2000);
		Keyboard k = new Keyboard(3);
		
		p.start();
		d.start();
		k.start(); 
		
	}
}