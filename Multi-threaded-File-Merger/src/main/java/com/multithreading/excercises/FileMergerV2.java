package com.multithreading.excercises;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileMergerV2 {

	public static void main(String[] args) throws InterruptedException{
		// TODO Auto-generated method stub
		final List<String> result = new ArrayList<String>();
		ExecutorService executor = Executors.newFixedThreadPool(args.length);
		CountDownLatch latch = new CountDownLatch(args.length);
		for(int i = 0; i < args.length - 1; i++) {
			final String filePath = args[i];
			Runnable thread = new Runnable() {				
				public void run() {
					// TODO Auto-generated method stub					
					try {
						BufferedReader reader = new BufferedReader(new FileReader(filePath));
						String line = reader.readLine();
						while(line!=null) {
							synchronized(result){
								result.add(line.toLowerCase());
							}
							line = reader.readLine();
						}
						reader.close();
						latch.countDown();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};			
			executor.submit(thread);
		}		
		latch.await();

		for(String val:result) {
			System.out.println(val);
		}
		Collections.sort(result);
		System.out.println("\nafter order\n");
		for(String val:result) {
			System.out.println(val);
		}
		
	}

	
	
}