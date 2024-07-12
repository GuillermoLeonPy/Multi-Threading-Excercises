package com.multithreading.excercises.banking.application.v4;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class BankingApplication {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		long beginApplication = System.currentTimeMillis();
		Account A = new Account("A", 1000);
		Account B = new Account("B", 1000);
		final int globalInitialBalance = A.getBalance()+B.getBalance();
		TransferProgram transferProgram = new TransferProgram();
		CountDownLatch startBreak = new CountDownLatch(1);
		Thread t1 = new Thread(new MyRunnable(B, A, transferProgram, startBreak));
		Thread t2 = new Thread(new MyRunnable(A, B, transferProgram, startBreak));
		t1.start();
		t2.start();
		startBreak.countDown();
		t1.join();
		System.out.println("Thread "+ t1.getId() + " finished at: " + Instant.now());
		t2.join();
		System.out.println("Thread "+ t2.getId() + " finished at: " + Instant.now());
		final int globalFinalBalance = A.getBalance()+B.getBalance();
		System.out.println("balance A+B="+globalFinalBalance + "; " + (globalFinalBalance==globalInitialBalance ?  "Integrity maintained" : "Integrity broken"));
		long endApplication = System.currentTimeMillis();
		System.out.println("Main Thread finished: synchronization at the balance property in the account class, by using AtomicInteger class"
				  +"\n time spent: " + (endApplication-beginApplication));
		
		
	}
}

class MyRunnable implements Runnable{
	private Account origin;
	private Account destination;
	private TransferProgram transferProgram;
	private CountDownLatch startBreak;
	
	public MyRunnable(Account origin, Account destination, TransferProgram transferProgram, CountDownLatch startBreak) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.transferProgram = transferProgram;
		this.startBreak = startBreak;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			startBreak.await();			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Thread " + Thread.currentThread().getId() + " started at: " + Instant.now());
		Random random = new Random();		
		for(int i = 0; i < 100000; i++) {
			//synchronized (transferProgram) {
			//synchronization at the account class level, on the withdraw and deposit methods
				transferProgram.doTransfer(origin, destination, random.nextInt(50));				
			//}
		}
	}
	
}

class Account{
	private AtomicInteger balance;
	private String accountNumber;

	public Account(String accountNumber,int balance) {
		super();
		this.balance = new AtomicInteger(balance);
		this.accountNumber = accountNumber;
	}
	
	public void withdraw(int amount) {
		//synchronized (this) {
			balance.addAndGet(-amount);
		//}		
	}
	
	public void deposit(int amount) {
		//synchronized (this) {
			balance.addAndGet(amount);
		//}		
	}

	public int getBalance() {
		return balance.get();
	}

	public String getAccountNumber() {
		return accountNumber;
	}	
}

class TransferProgram{	
	
	
	public TransferProgram() {
		super();
	}


	public void doTransfer(Account origin, Account destination, int amount) {
		//synchronized (this) {
		//synchronization at the account class level, on the withdraw and deposit methods
			origin.withdraw(amount);
			destination.deposit(amount);			
		//}		
	}
}
