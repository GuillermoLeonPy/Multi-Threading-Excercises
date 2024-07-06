package com.multithreading.excercises;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileMerger {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		final List<String> result = new ArrayList<String>();
		List<Thread> threads = new ArrayList<Thread>();
		for(int i = 0; i < args.length - 1; i++) {
			final String filePath = args[i];
			Thread thread = new Thread(new Runnable() {				
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
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			thread.start();
			threads.add(thread);
		}
		boolean finished = false;
		while(!finished) {
			finished = true;
			for(Thread t:threads) {
				finished = finished && !t.isAlive();
			}
		}
		
		Collections.sort(result);
		for(String val:result) {
			System.out.println(val);
		}
		
	}

	
	
}