class MyThread extends Thread{  
    public void run(){  
        System.out.println("shut down hook task completed..");  
    }  
}  
  
public class Demo{  
	public static void main(String[] args)throws Exception {  
		 
		Runtime runtime=Runtime.getRuntime();
		System.out.println("Number of process available on JVM: " + runtime.availableProcessors());
		System.out.println("Free memory: " + runtime.freeMemory());
		System.out.println("Total memory: " + runtime.totalMemory());
		System.out.println("Max memory: " + runtime.maxMemory());
		
		runtime.addShutdownHook(new MyThread()); 
		System.out.println("Now main sleeping... press ctrl+c to exit");  

		try{
			Thread.sleep(3000);
		} catch (Exception e) {
			
		}  
	}  
}