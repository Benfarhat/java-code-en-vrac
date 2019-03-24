import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

class MyRunnable4 implements Runnable {
	private final static Logger LOGGER = Logger.getLogger(MyRunnable4.class.getName());
	public static volatile Integer entier = 0;
	
	   public void run() {
	      for (int i = 0; i < 10; i++) {
	    	  // Not thread safe
	    	  // volatile keyword offer a better implementation of atomicity
	    	  // where data are "SUPPOSED TO BE" refresh before every new use.
	         System.out.println(Thread.currentThread().getName() + " - " + ++(Test.entier)); 
	         if (i % 3 == 0) {
	        	StringBuilder states = new StringBuilder();
	        	states.append(ThreadVolatileDemo04.threads.size() + " >-=x=-> ");

	     		for (Thread t: ThreadVolatileDemo04.threads) {
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


public class ThreadVolatileDemo04 {
	  
	public static List<Thread> threads = new ArrayList<Thread>();
	
	public static void main(String[] args) {
		
		ThreadGroup threadGroup = new ThreadGroup("MyThreadGroup");
		threads = new ArrayList<Thread>();
		for (int i=0; i<4; i++)
			threads.add(new Thread(threadGroup, new MyRunnable4()));
		
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
