package com.multithreading.excercises.lock_objects.deadlock;

public class DeadLock {

	private static Object lock1 = new Object();
	private static Object lock2 = new Object();
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Thread A = new Thread(new RunnableA(lock1, lock2));
		Thread B = new Thread(new RunnableB(lock2, lock1));
		A.start();
		B.start();
		while(true) {
			System.out.println("A state: "+A.getState());
			System.out.println("B state: "+B.getState());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

class RunnableA implements Runnable{

	private Object lock1;
	private Object lock2;
	
	public RunnableA(Object lock1, Object lock2) {
		super();
		this.lock1 = lock1;
		this.lock2 = lock2;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized (lock1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized (lock2) {

			}
		}
	}	
}

class RunnableB implements Runnable{
	
	private Object lock1;
	private Object lock2;
	
	public RunnableB(Object lock1, Object lock2) {
		super();
		this.lock1 = lock1;
		this.lock2 = lock2;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized (lock1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized (lock2) {
				
			}
		}
	}
	
}