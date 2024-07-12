package com.multithreading.excercises.lock_objects.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LockObjectExample {
	private static final int LOOP_QTY = 1000;
	Random random = new Random();
	List<Integer> list1 = new ArrayList<>();
	List<Integer> list2 = new ArrayList<>();
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		LockObjectExample app = new LockObjectExample();
		Thread t1 = new Thread(new MyRunnable(app));
		Thread t2 = new Thread(new MyRunnable(app));
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		int list1_size = app.list1.size();
		int list2_size = app.list2.size();
		System.out.println("End main thread; list1.size: " + list1_size + "; list2.size: " + list2_size);
		System.out.println((list1_size + list2_size) == (LOOP_QTY * 4) ? 
				"Integrity maintained" 
				+"\nexecution time reduced, locking at the lists properties level instead of instance level"
				+ "\n(syncrhonization at the method signature level)": 
					"Integrity broken because list1 size + list2 size shoud be: " + (LOOP_QTY * 4));
	}
	
	void mainProcess() throws InterruptedException {
		long beginTime = System.currentTimeMillis();
		process1();
		process2();
		long endTime = System.currentTimeMillis();
		System.out.println("Thread " + Thread.currentThread().getId() + " end main process in " + (endTime - beginTime));
	}
	
	private void process1() throws InterruptedException {
		synchronized(list1) {
			for(int i = 0; i < LOOP_QTY; i++) {
				Thread.sleep(1);
				list1.add(random.nextInt(50));
			}
		}
	}
	
	private void process2() throws InterruptedException {
		synchronized(list2) {
			for(int i = 0; i < LOOP_QTY; i++) {
				Thread.sleep(1);
				list2.add(random.nextInt(50));
			}
		}
	}
}

class MyRunnable implements Runnable{

	private LockObjectExample application;	
	
	public MyRunnable(LockObjectExample application) {
		super();
		this.application = application;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			application.mainProcess();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
