package com.multithreading.excercises.producer.consumer.on.list.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ListProducerConsumer {

	private static List<Integer> list = new ArrayList<>();
	private final static int LIMIT = 5;
	
	private static Lock lock = new ReentrantLock();
	private static Condition lockCondition = lock.newCondition();
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread producer = new Thread(new MyProducer(list, LIMIT, lock, lockCondition));
		Thread consumer = new Thread(new MyConsumer(list, lock, lockCondition));
		consumer.start();
		producer.start();
		consumer.join();
		producer.join();
	}

}

class MyProducer implements Runnable{

	private List<Integer> list;
	private int limit;	
	private Lock lock;
	private Condition lockCondition;
	public MyProducer(List<Integer> list, int limit, Lock lock, Condition lockCondition) {
		super();
		this.list = list;
		this.limit = limit;
		this.lock = lock;
		this.lockCondition = lockCondition;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int valueGenerator = 0;
		while(true) {
			lock.lock();				
				while(list.size() >= limit) {
					try {
						System.out.println("Producer Thread waiting...");
						lockCondition.await();
						System.out.println("Producer Thread resumed!");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("added element " + valueGenerator);
				list.add(valueGenerator++);
				lockCondition.signal();
			lock.unlock();			
		}
	}	
}
class MyConsumer implements Runnable{

	private List<Integer> list;
	private Lock lock;
	private int value;
	private Random random = new Random();
	private Condition lockCondition;
	
	public MyConsumer(List<Integer> list, Lock lock, Condition lockCondition) {
		super();
		this.list = list;
		this.lock = lock;
		this.lockCondition = lockCondition;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			lock.lock();
				try {
					System.out.println("Consumer Thread waiting...");
					lockCondition.await();
					System.out.println("Consumer Thread resumed!");
					value = list.remove(0);					
					System.out.println("Extracted element: " + value + "; list size: " + list.size());					
					Thread.sleep(random.nextInt(3000));
					lockCondition.signal();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			lock.unlock();
		}
	}	
}
