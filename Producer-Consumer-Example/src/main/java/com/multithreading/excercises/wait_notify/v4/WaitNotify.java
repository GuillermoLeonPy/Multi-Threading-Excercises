package com.multithreading.excercises.wait_notify.v4;

import java.util.Random;
/**
 * Synchronization on a lock object; ¡call wait() and notify() on the lock object!
 * @author test-user
 *
 */
class Message{
	Object lock = new Object();
	private String message;
	
	/**
	 * Synchronization on a lock object; ¡call wait() and notify() on the lock object!
	 * @param message
	 * @throws InterruptedException
	 */
	public void sendMessage(String message) throws InterruptedException {
		synchronized(lock) {
			Random random = new Random();
			Thread.sleep(random.nextInt(5000));
			this.message = message;
			System.out.println("message [" + message + "] send...");			
			lock.notify();
			lock.wait();			
		}		
	}
	
	/**
	 * Synchronization on a lock object; ¡call wait() and notify() on the lock object!
	 * @throws InterruptedException
	 */
	public void receiveMessage() throws InterruptedException {
		synchronized(lock) {
			lock.wait();
			System.out.println("message received: " + message);
			lock.notify();	
		}
	}
}
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
