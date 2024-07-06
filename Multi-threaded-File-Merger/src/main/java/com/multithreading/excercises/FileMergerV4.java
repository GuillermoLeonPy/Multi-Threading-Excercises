package com.multithreading.excercises;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileMergerV4 {

	public static void main(String[] args) throws InterruptedException{
		// TODO Auto-generated method stub
		final List<String> result = new ArrayList<String>();
		ExecutorService executor = Executors.newFixedThreadPool(args.length);
		CountDownLatch beginLatch = new CountDownLatch(1);
		CountDownLatch endLatch = new CountDownLatch(args.length-1);
		for(int i = 0; i < args.length - 1; i++) {
			final String filePath = args[i];			
			executor.submit(new MyRunnableV2(result, filePath, beginLatch, endLatch));
		}		
		
		beginLatch.countDown();
		endLatch.await();
		for(String val:result) {
			System.out.println(val);
		}
		endLatch = new CountDownLatch(1);
		executor.submit(new MyRunnableWriter(result, endLatch, args[args.length-1]));
		endLatch.await();
		executor.shutdownNow();
	}	
}
class MyRunnableV2 implements Runnable{

	private List<String> list;	
	private String filePath;
	private CountDownLatch endLatch;
	private CountDownLatch beginLatch;
	public MyRunnableV2(List<String> list, String filePath, CountDownLatch beginLatch, CountDownLatch endLatch) {
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
class MyRunnableWriter implements Runnable{
	private List<String> list;
	private CountDownLatch latch;
	private String filePath;
	
	public MyRunnableWriter(List<String> list, CountDownLatch latch, String filePath) {
		super();
		this.list = list;
		this.latch = latch;
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
			latch.countDown();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
}