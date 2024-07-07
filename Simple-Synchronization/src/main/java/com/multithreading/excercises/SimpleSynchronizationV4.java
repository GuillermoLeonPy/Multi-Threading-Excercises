package com.multithreading.excercises;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

class Counter{
	int synchronizedCounter;
	int counter;
}

public class SimpleSynchronizationV4 {

	private static final int LOOP_LIMIT = 5;
	Counter counterObject = new Counter();
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		SimpleSynchronizationV4 app = new SimpleSynchronizationV4();
		
		CountDownLatch latch1 = new CountDownLatch(2);
		Thread t1 = new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i = 1; i <= LOOP_LIMIT; i++) {
					try {
						app.incrementCounter();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				latch1.countDown();
			}
		});
		Thread t2 = new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i = 1; i <= LOOP_LIMIT; i++) {
					try {
						app.incrementCounter();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				latch1.countDown();
			}
		});
		t1.start();
		t2.start();
		latch1.await();
		System.out.println("without sync, counter = "+app.counterObject.counter + "; " + (app.counterObject.counter == (LOOP_LIMIT*2) ? "execution was synchronized!": "execution was NOT synchronized"));
		
		CountDownLatch latch2 = new CountDownLatch(2);
		Thread t3 = new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i = 1; i <= LOOP_LIMIT; i++) {
					try {
						app.syncrhonizedIncrementCounter();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				latch2.countDown();
			}
		});
		Thread t4 = new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i = 1; i <= LOOP_LIMIT; i++) {
					try {
						app.syncrhonizedIncrementCounter();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				latch2.countDown();
			}
		});
		
		t3.start();
		t4.start();
		latch2.await();
		System.out.println("with sync, counter = "+app.counterObject.synchronizedCounter + "; " + (app.counterObject.synchronizedCounter == (LOOP_LIMIT*2) ? "execution was synchronized!": "execution was NOT synchronized"));
	}

	private void incrementCounter() throws InterruptedException {
		
		Random random = new Random();
		int randomSleep = random.nextInt(1000);
		int currentVal = counterObject.counter;
		Thread.sleep(randomSleep);
		counterObject.counter = currentVal + 1;		
	}
	
	private void syncrhonizedIncrementCounter() throws InterruptedException {
		
		Random random = new Random();
		int randomSleep = random.nextInt(1000);
		synchronized(counterObject){
			int currentVal = counterObject.synchronizedCounter;
			Thread.sleep(randomSleep);
			counterObject.synchronizedCounter = currentVal + 1;	
		}				
	}
}
