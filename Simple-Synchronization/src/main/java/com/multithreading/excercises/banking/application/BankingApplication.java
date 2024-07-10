package com.multithreading.excercises.banking.application;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class BankingApplication {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
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
		System.out.println("Main Thread finished: synchronization inside the transfer program method");
		
		
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
				transferProgram.doTransfer(origin, destination, random.nextInt(50));				
			//}
		}
	}
	
}

class Account{
	private int balance;
	private String accountNumber;

	public Account(String accountNumber,int balance) {
		super();
		this.balance = balance;
		this.accountNumber = accountNumber;
	}
	
	public void withdraw(int amount) {
		balance-=amount;
	}
	
	public void deposit(int amount) {
		balance+=amount;
	}

	public int getBalance() {
		return balance;
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
		synchronized (this) {
			origin.withdraw(amount);
			destination.deposit(amount);			
		}		
	}
}
