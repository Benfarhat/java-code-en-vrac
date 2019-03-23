import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

class MyRunnable1 implements Runnable {
	private final static Logger LOGGER = Logger.getLogger(MyRunnable1.class.getName());
	public static Integer entier = 0;
	   public void run() {
	      for (int i = 0; i < 10; i++) {
	         System.out.println(Thread.currentThread().getName() + " - " + ++(Test.entier)); 
	         if (i % 3 == 0) {
	        	StringBuilder states = new StringBuilder();
	        	states.append(ThreadDemo01.threads.size() + " >-=x=-> ");

	     		for (Thread t: ThreadDemo01.threads) {
	    			states.append(t.getName() + " : " + t.getState() + " | ");
	    		}
	     		LOGGER.info(states.toString());
	         }
	         try {
	            Thread.sleep(2000);
	         } catch (InterruptedException e) {
	            e.printStackTrace();
	         }
	      }
	   }
	}


public class ThreadDemo01 {
	  
	public static List<Thread> threads = new ArrayList<Thread>();
	
	public static void main(String[] args) {
		threads = new ArrayList<Thread>();
		for (int i=0; i<4; i++)
			threads.add(new Thread(new MyRunnable1()));
		
		for (Thread t: threads) {
			t.start();
		}

	}

}
