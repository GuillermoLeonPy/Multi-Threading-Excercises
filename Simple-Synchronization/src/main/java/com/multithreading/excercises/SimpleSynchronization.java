package com.multithreading.excercises;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class SimpleSynchronization {

	private static final int LOOP_LIMIT = 5;
	private int syncrhonizedCounter;
	private int counter;
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		SimpleSynchronization app = new SimpleSynchronization();
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
		System.out.println("without sync, counter = "+app.counter + "; " + (app.counter == (LOOP_LIMIT*2) ? "execution was synchronized!": "execution was NOT synchronized"));
		
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
		System.out.println("with sync, counter = "+app.syncrhonizedCounter + "; " + (app.syncrhonizedCounter == (LOOP_LIMIT*2) ? "execution was synchronized!": "execution was NOT synchronized"));
	}

	private void incrementCounter() throws InterruptedException {
		
		Random random = new Random();
		int randomSleep = random.nextInt(1000);
		int currentVal = counter;
		Thread.sleep(randomSleep);
		counter = currentVal + 1;		
	}
	
	private void syncrhonizedIncrementCounter() throws InterruptedException {
		
		Random random = new Random();
		int randomSleep = random.nextInt(1000);
		synchronized(this){
			int currentVal = syncrhonizedCounter;
			Thread.sleep(randomSleep);
			syncrhonizedCounter = currentVal + 1;	
		}				
	}
}
