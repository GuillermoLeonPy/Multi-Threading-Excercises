package com.multithreading.excercises;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileMergerV3 {

	public static void main(String[] args) throws InterruptedException{
		// TODO Auto-generated method stub
		final List<String> result = new ArrayList<String>();
		ExecutorService executor = Executors.newFixedThreadPool(args.length);
		CountDownLatch beginLatch = new CountDownLatch(1);
		CountDownLatch endLatch = new CountDownLatch(args.length);
		for(int i = 0; i < args.length - 1; i++) {
			final String filePath = args[i];			
			executor.submit(new MyRunnable(result, filePath, beginLatch, endLatch));
		}		
		
		beginLatch.countDown();
		endLatch.await();
		for(String val:result) {
			System.out.println(val);
		}
		Collections.sort(result);
		System.out.println("\nafter order\n");
		for(String val:result) {
			System.out.println(val);
		}
		executor.shutdownNow();
	}	
}
class MyRunnable implements Runnable{

	private List<String> list;	
	private String filePath;
	private CountDownLatch endLatch;
	private CountDownLatch beginLatch;
	public MyRunnable(List<String> list, String filePath, CountDownLatch beginLatch, CountDownLatch endLatch) {
		super();
		this.list = list;
		this.filePath = filePath;
		this.beginLatch = beginLatch;
		this.endLatch = endLatch;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			beginLatch.await();
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			while(line != null) {
				synchronized (list) {
					list.add(line.toLowerCase());
				}
				line = reader.readLine();
			}
			reader.close();
			endLatch.countDown();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}