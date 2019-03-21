import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;

public class RunProgramDemo {

    public static void main(String[] args) throws IOException {
        String application = (args.length > 0) ? args[0] : "notepad";
        int timeout = 20 * 1000;
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
                process.destroy();
                
                if (process != null && process.isAlive()) {
                    process.destroyForcibly();
                    System.out.println( "Process " + process.pid() + " destroyed by force.");
                } else {
                    System.out.println( "Process " + process.pid() + " destroyed.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
