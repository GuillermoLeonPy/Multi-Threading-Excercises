package com.multithreading.excercises;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileMergerV5 {

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException{
		// TODO Auto-generated method stub
		final List<String> result = new ArrayList<String>();
		ExecutorService executor = Executors.newFixedThreadPool(args.length);
		//barrier initialized with number of threads + 1: the thread from which
		//we want to trigger the execution
		CyclicBarrier startBarrier = new CyclicBarrier(args.length);
		
		for(int i = 0; i < args.length - 1; i++) {
			final String filePath = args[i];			
			executor.submit(new MyRunnableV3(result, filePath, startBarrier));
		}		
		
		startBarrier.await();
		executor.awaitTermination(5, TimeUnit.SECONDS);
		for(String val:result) {
			System.out.println(val);
		}
		
		executor.submit(new MyRunnableWriterV2(result, args[args.length-1]));
		executor.awaitTermination(5, TimeUnit.SECONDS);
		executor.shutdownNow();
	}	
}
class MyRunnableV3 implements Runnable{

	private List<String> list;	
	private String filePath;
	private CyclicBarrier startBarrier;
	public MyRunnableV3(List<String> list, String filePath, CyclicBarrier startBarrier) {
		super();
		this.list = list;
		this.filePath = filePath;
		this.startBarrier = startBarrier;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			startBarrier.await();
			System.out.println("Thread " + Thread.currentThread().getId() + " started ! -> " + Instant.now());
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			while(line != null) {
				synchronized (list) {
					list.add(line.toLowerCase());
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException | InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
class MyRunnableWriterV2 implements Runnable{
	private List<String> list;
	private String filePath;
	
	public MyRunnableWriterV2(List<String> list, String filePath) {
		super();
		this.list = list;
		this.filePath = filePath;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Collections.sort(list);
			Random random = new Random();
			String outPutFilePath = filePath + "/output_" + random.nextInt(99) + ".txt";
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outPutFilePath)));
			System.out.println("\nfile:" + outPutFilePath + "\n\nafter order\n");
			for(String val:list) {			
				System.out.println(val);
				writer.append(val);
				writer.newLine();
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
}