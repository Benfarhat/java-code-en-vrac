import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Producer implements Runnable{
	
	private final BlockingQueue<String> queue;
	
	Producer(BlockingQueue<String> queue){
		this.queue = queue;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
			System.err.println("The interrupted status of the current thread is cleared when this exception is thrown.");
			System.err.println(ie.getMessage());
		}
		
		System.out.println("Starting producer : " + Thread.currentThread().getName());
		
		try {
			for(int i = 0; i < 10; i++) {
				queue.put(String.valueOf(i));
			}

			queue.put("*STOP*");
		} catch (Exception e) {
			System.out.println(Thread.currentThread().getName() + " - " + e.getMessage());
		}
		
	}
	
}

class Consumer implements Runnable{
	private final BlockingQueue<String> queue;
	
	Consumer(BlockingQueue<String> queue) {
		this.queue = queue;
	}
	
	public void run() {
		
		try {
			System.out.println("Starting consumer : " + Thread.currentThread().getName());
			
			String value = queue.take();
			
			while(!value.equals("*STOP*")) {
				System.out.println(Thread.currentThread().getName() + " - Receiving from Producer : " + value);
				value = queue.take();
			}
		} catch (Exception e) {
			System.out.println(Thread.currentThread().getName() + " - " + e.getMessage());
		}
		
		
	}
}




public class ThreadCommunicationDemo10 {
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<String> q = new LinkedBlockingQueue<>();
		
		Thread p1 = new Thread(new Producer(q));
		Thread c1 = new Thread(new Consumer(q));
		Thread c2 = new Thread(new Consumer(q));
		Thread c3 = new Thread(new Consumer(q));
		
		p1.start();
		c1.start();
		c2.start();
		c3.start();	
		
		p1.join();
		c1.join();
		c2.join();
		c3.join();
	}
}
