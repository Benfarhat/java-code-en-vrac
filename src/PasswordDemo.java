import java.io.Console;

public class PasswordDemo {
    public static void main(String[] args) {
        try {
            Console console = System.console();
            if(console != null) {
                char [] password = console.readPassword("Enter your password: ");
                console.printf("Your password is: %s\n", String.valueOf(password));
            } else {
                System.out.println("Couldn't get Console instance");
                System.exit(0);
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
