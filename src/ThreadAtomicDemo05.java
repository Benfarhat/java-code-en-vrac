import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

class MyRunnable5 implements Runnable {
	private final static Logger LOGGER = Logger.getLogger(MyRunnable5.class.getName());
	public static AtomicInteger entier = new AtomicInteger();
	public static AtomicInteger entier2 = new AtomicInteger();
	   public void run() {

	      for (int i = 0; i < 4; i++) {
	    	  // Not thread safe
	    	  // Increment,get and display should be use together
	    	  // We've got finnaly 16 (4 loop * 4 thread)
	    	  // But in the beggining, we displaying the same value
	    	  MyRunnable5.entier2.incrementAndGet();
	    	  System.out.println("* " + Thread.currentThread().getName() + " - " + (MyRunnable5.entier2)); 
	      }
         try {
	            Thread.sleep(2000);
	        	System.err.println();
	         } catch (InterruptedException e) {
	        	 e.printStackTrace();
	         }   
	      for (int i = 0; i < 10; i++) {
	    	  // Thread safe
	    	  // Atomicity should be use with care
	         System.out.println(Thread.currentThread().getName() + " - " + (MyRunnable5.entier.incrementAndGet())); 
	         if (i % 3 == 0) {
	        	StringBuilder states = new StringBuilder();
	        	states.append(ThreadAtomicDemo05.threads.size() + " >-=x=-> ");

	     		for (Thread t: ThreadAtomicDemo05.threads) {
	    			states.append(t.getName() + " : " + t.getState() + " | ");
	    		}
	     		LOGGER.info(states.toString());
	         }
	         try {
	            Thread.sleep(2000);
	         } catch (InterruptedException e) {
	        	System.err.println(e.getMessage() + " - Thread Group interrupted!!");
	         }
	      }

	   }
	}


public class ThreadAtomicDemo05 {
	  
	public static List<Thread> threads = new ArrayList<Thread>();
	
	public static void main(String[] args) {
		
		ThreadGroup threadGroup = new ThreadGroup("MyThreadGroup");
		threads = new ArrayList<Thread>();
		for (int i=0; i<4; i++)
			threads.add(new Thread(threadGroup, new MyRunnable5()));
		
		for (Thread t: threads) {
			t.start();
		}
		
		try {
			Thread.currentThread();
			Thread.sleep(5000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		
		threadGroup.interrupt();

	}

}
