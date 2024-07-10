package com.multithreading.excercises;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * BlockingQueue: put and take methods; waits for room and for any element respectively, in order to continue execution
 * @author test-user
 *
 */
public class ProducerConsumerExample {

	private BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ProducerConsumerExample app = new ProducerConsumerExample();
		Thread producer = new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					app.producer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Thread consumer = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					app.consumer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		consumer.start();
		producer.start();
		
		//consumer.join();
		//producer.join();
		System.out.println("main thread finalized; threads continue running without the need of calling join on them, look at the ouput...");
	}
	/**
	 * BlockingQueue put method: waits for the queue to have room for adding an element
	 * @throws InterruptedException
	 */
	private void producer() throws InterruptedException {
		Random random = new Random();
		while(true) {
			queue.put(random.nextInt(100));
		}
	}
	
	/**
	 * BlockingQueue take method: waits for the queue to have any element to be retrieved
	 * @throws InterruptedException
	 */
	private void consumer() throws InterruptedException {
		Random random = new Random();
		int val = 0;
		while(true) {
			val = queue.take();
			System.out.println("retrieved value: " + val + "; queue size: " + queue.size());
			Thread.sleep(random.nextInt(3000));
		}
	}

}
