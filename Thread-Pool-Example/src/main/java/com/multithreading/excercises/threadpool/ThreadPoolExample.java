package com.multithreading.excercises.threadpool;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ExecutorService executor = Executors.newFixedThreadPool(2);
		for(int i = 0; i < 10; i++) {
			executor.submit(new MyRunnable(i));
		}
		System.out.println("waiting for all threads to finalize...");
		executor.awaitTermination(1, TimeUnit.HOURS);
		System.out.println("program finalized");
	}

}

class MyRunnable implements Runnable{

	private int id;
	
	public MyRunnable(int id) {
		super();
		this.id = id;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(" started thread with id: " + id);
		Random random = new Random();
		try {
			Thread.sleep(random.nextInt(1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("finished thread with id: " + id);
	}
	
}
