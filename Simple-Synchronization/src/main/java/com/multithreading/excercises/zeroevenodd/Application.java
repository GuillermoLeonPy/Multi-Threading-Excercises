package com.multithreading.excercises.zeroevenodd;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(5);
		Thread zeroT = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					zeroEvenOdd.zero((int i)-> {System.out.println(i);});
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Thread evenT = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					zeroEvenOdd.even((int i)-> {System.out.println(i);});
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Thread oddT = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					zeroEvenOdd.odd((int i)-> {System.out.println(i);});
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		oddT.start();
		evenT.start();
		zeroT.start();
		
	}

}
