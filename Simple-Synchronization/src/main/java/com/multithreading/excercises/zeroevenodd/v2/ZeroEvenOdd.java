package com.multithreading.excercises.zeroevenodd.v2;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class ZeroEvenOdd {
    private int n;
    private int i = 0;
    private Semaphore zeroSemaphore = new Semaphore(1);
    private Semaphore evenSemaphore = new Semaphore(0);
    private Semaphore oddSemaphore = new Semaphore(0);
    private boolean printZero = true;
    private Semaphore checkSemaphore = new Semaphore(1);
    
    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    private void check() throws InterruptedException {
    	checkSemaphore.acquire();
    	if(printZero) {
    		printZero = false;
    		i++;
    		if(i % 2 == 0) {
    			evenSemaphore.release();
    		}else {
    			oddSemaphore.release();
    		}
    		
    	}else {
    		printZero = true;
    		zeroSemaphore.release();
    	}
    	checkSemaphore.release();
    }
    
    private void finish() {
    	i=n+1;
    	zeroSemaphore.release();
    	evenSemaphore.release();
    	oddSemaphore.release();
    }
    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
    	while(i <= n) {
    		zeroSemaphore.acquire();
    		if(i>=n) {
    			finish();
    			return;
    		}
    		printNumber.accept(0);
    		check();
    	}
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        while(i <= n) {
        	evenSemaphore.acquire();
        	if(i>n) {
        		finish();
    			return;
    		}
        	printNumber.accept(i);
        	check();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        while(i <= n) {
        	oddSemaphore.acquire();
        	if(i>n) {
        		finish();
    			return;
    		}
        	printNumber.accept(i);
        	check();
        }
    }
}
