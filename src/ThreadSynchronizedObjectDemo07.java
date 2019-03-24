
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

class SynchronizedObject {
   private int entier = 0;
   public Integer incrementeAndGet() {
	      synchronized (this){
	    	  entier++;
	         return entier;
	      }
   }
   public void incremente(){
      synchronized(this){
         entier++;
      }
   }
   public Integer get(){
      synchronized (this){
         return entier;
      }
   }
}
class MyRunnable7 implements Runnable {
	private final static Logger LOGGER = Logger.getLogger(MyRunnable7.class.getName());
	public static Integer entier = 0;
	public static SynchronizedObject synchronizedEntier = new SynchronizedObject();
	
	   public void run() {
	      for (int i = 0; i < 10; i++) {
	    	  // Not thread safe
	    	  // If you see in console, some thread have the same "entier" value
	         System.out.println("NOT THREAD SAFE> " + Thread.currentThread().getName() + " - " + ++(MyRunnable7.entier)); 

	         System.out.println("THREAD SAFE> " + Thread.currentThread().getName() + " - " + MyRunnable7.synchronizedEntier.incrementeAndGet());
	         if (i % 3 == 0) {
	        	StringBuilder states = new StringBuilder();
	        	states.append(ThreadDemo02.threads.size() + " >-=x=-> ");

	     		for (Thread t: ThreadDemo02.threads) {
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


public class ThreadSynchronizedObjectDemo07 {
	  
	public static List<Thread> threads = new ArrayList<Thread>();
	
	public static void main(String[] args) {
		
		ThreadGroup threadGroup = new ThreadGroup("MyThreadGroup");
		threads = new ArrayList<Thread>();
		for (int i=0; i<4; i++)
			threads.add(new Thread(threadGroup, new MyRunnable7()));
		
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

