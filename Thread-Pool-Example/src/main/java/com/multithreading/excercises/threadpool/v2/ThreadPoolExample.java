package com.multithreading.excercises.threadpool.v2;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExample {

	private static final int THREADS_QTY = 5;
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		threadPoolExample();
		threadsWithOutPool();
		threads_dont_die_if_main_thread_finalize();
	}
	
	private static void threads_dont_die_if_main_thread_finalize() {
		System.out.println("\n******************************\n");
		System.out.println("\nthreads DON'T DIE if main thread FINALIZE\n");
		CountDownLatch latch = new CountDownLatch(THREADS_QTY);
		for(int i = 0; i < THREADS_QTY; i++) {
			Thread t = new Thread(new MyRunnable(i,latch));
			t.start();
		}
		/*
		¡no wait for threads finalize signal!
		latch.await();
		*/
		System.out.println("");
		System.out.println("main thread finalized, LOOK that THREADS COMPLETE THEIR EXECUTION even after main thread finalization");
	}
	
	private static void threadsWithOutPool() throws InterruptedException {
		System.out.println("\nexample without a thread pool\n");
		CountDownLatch latch = new CountDownLatch(THREADS_QTY);
		for(int i = 0; i < THREADS_QTY; i++) {
			Thread t = new Thread(new MyRunnable(i,latch));
			t.start();
		}
		System.out.println("waiting for all threads to finalize...");
		latch.await();
		System.out.println("");
		System.out.println("main thread finalized after all threads finalized, look the print");
	}

	private static void threadPoolExample() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		CountDownLatch latch = new CountDownLatch(THREADS_QTY);
		for(int i = 0; i < THREADS_QTY; i++) {
			executor.submit(new MyRunnable(i,latch));
		}
		System.out.println("waiting for all threads to finalize...");
		latch.await();
		executor.shutdownNow();
		System.out.println("main thread finalized after all threads finalized, look the print");		
	}
	
}

class MyRunnable implements Runnable{

	private int id;
	private CountDownLatch latch;
	
	public MyRunnable(int id, CountDownLatch latch) {
		super();
		this.id = id;
		this.latch = latch;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println(" started thread with id: " + id);
			Random random = new Random();
			Thread.sleep(random.nextInt(5000));
			System.out.println("finished thread with id: " + id);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			latch.countDown();
		}
	}
	
}
