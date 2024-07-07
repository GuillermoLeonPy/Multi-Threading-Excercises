package com.multithreading.excercises;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
/**
 * Bad synchronization approach example
 * @author test-user
 *
 */
public class SimpleSynchronizationV2 {

	private static final int LOOP_LIMIT = 5;
	private Integer synchronizedCounter = 0;
	private Integer counter = 0;
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("Bad synchronization approach example :(");
		SimpleSynchronizationV2 app = new SimpleSynchronizationV2();
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
		System.out.println("with sync, counter = "+app.synchronizedCounter + "; " + (app.synchronizedCounter == (LOOP_LIMIT*2) ? "execution was synchronized!": "execution was NOT synchronized"));
	}

	private void incrementCounter() throws InterruptedException {
		
		Random random = new Random();
		int randomSleep = random.nextInt(1000);
		int currentVal = counter;
		Thread.sleep(randomSleep);
		counter = currentVal + 1;		
	}
	/**
	 * Results in unsync processing
	 * @throws InterruptedException
	 */
	private void syncrhonizedIncrementCounter() throws InterruptedException {
		
		Random random = new Random();
		int randomSleep = random.nextInt(1000);
		/*Results in unsync processing*/
		synchronized(synchronizedCounter){
			int currentVal = synchronizedCounter;
			Thread.sleep(randomSleep);
			synchronizedCounter = currentVal + 1;	
		}				
	}
}
