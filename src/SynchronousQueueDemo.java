import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Test implements Runnable {
	public static Integer entier = 0;
	   public void run() {
	      for (int i = 0; i < 10; i++) {
	         System.out.println(Thread.currentThread().getName() + " - " + ++(Test.entier));         
	         try {
	            Thread.sleep(2000);
	         } catch (InterruptedException e) {
	            e.printStackTrace();
	         }
	      }
	   }
	}


public class SynchronousQueueDemo {
	   

	public static void main(String[] args) {
	      Thread t1 = new Thread(new Test());
	      Thread t2 = new Thread(new Test());
	      Thread t3 = new Thread(new Test());
	      Thread t4 = new Thread(new Test());
	      
	      t1.start();
	      t2.start();
	      t3.start();
	      t4.start();
	     
		/*
		ExecutorService executor = Executors.newFixedThreadPool(2);
		AtomicInteger sharedState = new AtomicInteger();
		CountDownLatch countDownLatch = new CountDownLatch(1);
		
		Runnable producer = () -> {
		    Integer producedElement = ThreadLocalRandom
		      .current()
		      .nextInt();
		    sharedState.set(producedElement);
		    countDownLatch.countDown();
		};
		
		Runnable consumer = () -> {
		    try {
		        countDownLatch.await();
		        Integer consumedElement = sharedState.get();
		    } catch (InterruptedException ex) {
		        ex.printStackTrace();
		    }
		};
		
		executor.execute(producer);
		executor.execute(consumer);

		executor.awaitTermination(500, TimeUnit.MILLISECONDS);
		executor.shutdown();
		assertEquals(countDownLatch.getCount(), 0);
		
*/
	}

}
