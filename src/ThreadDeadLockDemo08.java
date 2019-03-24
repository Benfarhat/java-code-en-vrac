
public class ThreadDeadLockDemo08 {
	public static void main(String[] args) {
		System.out.println("Create Deadlock with nested Synchronized!");
		ThreadDeadLockDemo08 test = new ThreadDeadLockDemo08();
 
        final A a = test.new A();
        final B b = test.new B();
 
        // Thread-1
        Runnable block1 = new Runnable() {
            public void run() {
            	System.out.println("Thread-1 - synchronized ressource A");
                synchronized (a) {
                	System.out.println("Thread-1 - Done!");
                    try {
                        // Adding delay so that both threads can start trying to
                        // lock resources
                    	System.out.println("Thread-1 - sleeping for 500 ms");
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                	System.out.println("Thread-1 - synchronized ressource B");
                    // Thread-1 have A but need B also
                    synchronized (b) {
                        System.out.println("Thread-1 - In block 1");
                    }
                }
            }
        };
 
        // Thread-2
        Runnable block2 = new Runnable() {
            public void run() {

            	System.out.println("Thread-2 - synchronized ressource B");
                synchronized (b) {
                    // Thread-2 have B but need A also
                	System.out.println("Thread-2 - Trying to synchronized ressource A wich need resource B...");
                    synchronized (a) {
                        System.out.println("Thread-2 - In block 2");
                    }
                }
            }
        };
 
        new Thread(block1).start();
        new Thread(block2).start();
    }
 
	 
    // Resource A
    private class A {
        private int i = 10;
 
        public int getI() {
            return i;
        }
 
        public void setI(int i) {
            this.i = i;
        }
    }
 
    // Resource B
    private class B {
        private int i = 20;
 
        public int getI() {
            return i;
        }
 
        public void setI(int i) {
            this.i = i;
        }
    }
}