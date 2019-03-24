
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Random;
import java.util.StringTokenizer;

class MyUncaughtExceptionHandler implements UncaughtExceptionHandler{

   public void uncaughtException(Thread t, Throwable e) {
	   
		StringTokenizer token = new StringTokenizer(e.getClass().getName(), ".");
		int position = 0;
		String exceptionName = e.getClass().getName();
		while (token.hasMoreTokens()) {
			String elem = token.nextToken();
			if(position++ == 2)
				exceptionName = elem;
		}
      System.out.print("[ " + t.getName() + " ] - Exception of type : " +exceptionName);

      /*
       * Here we can find console message caused by more than one thread trying to display message in the same time
       * for example in line 5 & 6 and 7 and 8
       * 
       * 1 [ Thread-1 ] - Exception of type : ArithmeticException occured in Thread-1
       * 2 [ Thread-2 ] - Exception of type : ArrayIndexOutOfBoundsException occured in Thread-2
       * 3 [ Thread-3 ] - Exception of type : ClassCastException occured in Thread-3
       * 4 [ Thread-4 ] - Exception of type : ClassCastException occured in Thread-4
       * 5 [ Thread-5 ] - Exception of type : ClassCastException[ Thread-6 ] - Exception of type : ArithmeticException occured in Thread-6
       * 6 occured in Thread-5
       * 7 [ Thread-7 ] - Exception of type : ArrayIndexOutOfBoundsException[ Thread-0 ] - Exception of type : ClassCastException occured in Thread-0
       * 8 occured in Thread-7
       */
      System.out.println(" occured in " + t.getName());
   }
}
class ThreadException extends Thread{

	   public ThreadException (String name){
	      setName(name);
	      setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
	   }
	   public void run(){
	      Random rand = new Random();
	      while(true){
	         //System.out.println(" - " + getName());
	         int cas = rand.nextInt(7);
	         
	         switch(cas){
	            case 0 :
	               int number = 10 / 0;
	               break;
	            case 1 :
	               Object str = "a text";
	               double dbl = (double) str;
	               break;  
	            case 2:
	            	String string = new String().concat(new StringBuilder().toString());
	            	break;
	            case 3:
	            	String[] array = new String[3];
	            	String elem = array[5];
	            	break;
	            case 4:
	            	File file = new File("/unexistFile.blabla");
	            	break;
	            default : 
	               // System.out.println("No error detected...");
	               break;
	         }
	      }
	   }
	}

public class ThreadDemo03 {
	   public static void main(String[] args){
	      
	      for(int i = 0; i < 8; i++){
	         Thread t = new ThreadException("Thread-" + i);
	         t.start();
	      }
	   }
	}