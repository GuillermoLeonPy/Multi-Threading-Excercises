package com.multithreading.excercises.zeroevenodd;

import java.util.function.IntConsumer;

public class ZeroEvenOdd {
    private int n;
    private int i = 0;
    private boolean zeroFlag = true;
    private boolean evenFlag = false;
    private boolean oddFlag = false;
    private Object lock = new Object();


    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    private void check(){
        if(zeroFlag){
            i++;
            if(i % 2 == 0){
                evenFlag = true;
                oddFlag = false;
            }else{
                evenFlag = false;
                oddFlag = true;
            }
            zeroFlag = false;
            
        }else{
            zeroFlag = true;
            evenFlag = false;
            oddFlag = false;
        }
    }
    
    private void finish() {
    	i=n+1;
        zeroFlag = true;
        evenFlag = true;
        oddFlag = true;    	
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        while(i<=n){
            synchronized(lock){                
                    while(!zeroFlag){
                        lock.wait();
                    }
                    if(i>=n) {
                    	finish();
                    	lock.notifyAll();
                    	return;
                    }
                    printNumber.accept(0);  
                    check();          
                    lock.notifyAll();            
                
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        while(i<=n){
            synchronized(lock){
                
                    while(!evenFlag){
                        lock.wait();
                    }
                    if(i>n) {
                    	finish();
                    	lock.notifyAll();
                    	return;
                    }
                    printNumber.accept(i);
                    check();          
                    lock.notifyAll();
                
                
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        while(i <= n){
            synchronized(lock){
                
                    while(!oddFlag){
                        lock.wait();
                    }  
                    if(i>n) {
                    	finish();
                    	lock.notifyAll();
                    	return;
                    }
                    printNumber.accept(i);
                    check();          
                    lock.notifyAll();
                
            }
        }
    }
}
