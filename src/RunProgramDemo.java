import java.io.IOException;

public class RunProgramDemo {

    public static void main(String[] args) throws IOException {
        String application = (args.length > 0) ? args[0] : "notepad";
        int timeout = 10 * 1000;
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {
            process = runtime.exec(application);
            System.out.println( "Process " + process.pid() + " started.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                Thread.sleep(timeout);
                if ( process != null ) {
                    System.err.println("A problem is detected");
                }
                process.destroy();
                
                if (process != null && process.isAlive()) {
                    process.destroyForcibly();
                    System.err.println( "Process " + process.pid() + " destroyed by force.");
                } else {
                    System.out.println( "Process " + process.pid() + " destroyed.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
