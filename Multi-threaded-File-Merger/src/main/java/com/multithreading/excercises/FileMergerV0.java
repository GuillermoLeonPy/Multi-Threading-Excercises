package com.multithreading.excercises;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileMergerV0 {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		final List<String> result = new ArrayList<String>();
		List<Thread> threads = new ArrayList<Thread>();
		for(int i = 0; i < args.length - 1; i++) {
			final String filePath = args[i];
			Thread thread = new Thread(new Runnable() {				
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("Thread " + Thread.currentThread().getId() + "   started at: " + Instant.now());
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
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally {
						System.out.println("Thread " + Thread.currentThread().getId() + " finalized at: " + Instant.now());
					}
				}
			});			
			threads.add(thread);
		}
		for(Thread t:threads) {
			try {				
				t.start();
				t.join();//Waits for this thread to die.
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Collections.sort(result);
		for(String val:result) {
			System.out.println(val);
		}
		
	}

	
	
}