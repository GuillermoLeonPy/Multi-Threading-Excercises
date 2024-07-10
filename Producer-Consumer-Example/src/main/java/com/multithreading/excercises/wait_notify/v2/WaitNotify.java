package com.multithreading.excercises.wait_notify.v2;

import java.util.Random;
/**
 * Synchronization on a lock object: wait() and notify() must be called ON the LOCK OBJECT!
 * @author test-user
 *
 */
class Message{
	Object lock = new Object();
	private String message;
	
	/**
	 * Synchronization on a lock object: wait() and notify() must be called ON the LOCK OBJECT!
	 * look at the package:com.multithreading.excercises.wait_notify.v4
	 * @param message
	 * @throws InterruptedException
	 */
	public void sendMessage(String message) throws InterruptedException {
		synchronized(lock) {
			Random random = new Random();
			Thread.sleep(random.nextInt(5000));
			this.message = message;
			System.out.println("message [" + message + "] send...");			
			notify();
			wait();			
		}		
	}
	
	/**
	 * Synchronization on a lock object: wait() and notify() must be called ON the LOCK OBJECT!
	 * look at the package:com.multithreading.excercises.wait_notify.v4
	 * @throws InterruptedException
	 */
	public void receiveMessage() throws InterruptedException {
		synchronized(lock) {
			wait();
			System.out.println("message received: " + message);
			notify();	
		}
	}
}

/**
 * Synchronization on a lock object: wait() and notify() must be called ON the LOCK OBJECT!
 * @author test-user
 *
 */
public class WaitNotify {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Message message = new Message();
		Thread sender = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int i = 0;
				while(true) {
					try {
						message.sendMessage("message nro " + i++);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		Thread receiver = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					try {
						message.receiveMessage();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		receiver.start();
		sender.start();
		
	}

}
