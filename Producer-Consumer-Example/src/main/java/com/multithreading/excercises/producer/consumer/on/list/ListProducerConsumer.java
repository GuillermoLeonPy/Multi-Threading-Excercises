package com.multithreading.excercises.producer.consumer.on.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListProducerConsumer {

	private static List<Integer> list = new ArrayList<>();
	private final static int LIMIT = 5;
	private static Object lock = new Object();
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread producer = new Thread(new MyProducer(list, LIMIT, lock));
		Thread consumer = new Thread(new MyConsumer(list, lock));
		consumer.start();
		producer.start();
		consumer.join();
		producer.join();
	}

}

class MyProducer implements Runnable{

	private List<Integer> list;
	private int limit;	
	private Object lock;
	public MyProducer(List<Integer> list, int limit, Object lock) {
		super();
		this.list = list;
		this.limit = limit;
		this.lock = lock;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int valueGenerator = 0;		
		while(true) {
			synchronized(lock) {				
				while(list.size() >= limit) {
					try {
						System.out.println("Producer Thread waiting...");
						lock.wait();
						System.out.println("Producer Thread resumed!");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("added element: "+valueGenerator + " to the list");
				list.add(valueGenerator++);				
				lock.notify();
			}			
		}
	}	
}
class MyConsumer implements Runnable{

	private List<Integer> list;
	private Object lock;
	private int value;
	private Random random = new Random();
	
	public MyConsumer(List<Integer> list, Object lock) {
		super();
		this.list = list;
		this.lock = lock;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			synchronized(lock) {
				try {
					System.out.println("Consumer Thread waiting...");
					lock.wait();
					System.out.println("Consumer Thread resumed!");
					value = list.remove(0);
					System.out.println("Extracted element: " + value + "; list size: " + list.size());					
					Thread.sleep(random.nextInt(3000));
					lock.notify();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}	
}
