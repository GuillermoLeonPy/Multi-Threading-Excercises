package com.multithreading.excercises.wait_notify;

import java.util.Random;

class Message{
	
	private String message;
	
	public void sendMessage(String message) throws InterruptedException {
		synchronized(this) {
			Random random = new Random();
			Thread.sleep(random.nextInt(5000));
			this.message = message;
			System.out.println("message [" + message + "] send...");			
			notify();
			wait();			
		}		
	}
	
	public void receiveMessage() throws InterruptedException {
		synchronized (this) {
			wait();
			System.out.println("message received: " + message);
			notify();	
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
