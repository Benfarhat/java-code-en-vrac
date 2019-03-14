import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

enum Feu {
    ORANGE,VERT,ROUGE
}

public class Demo {
    
    static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
    
    public static void main(String[] args) throws Exception {
        Feu feu = Feu.VERT;
        System.out.println("Action : ...");
        String action = keyboard.readLine();
        System.out.println(System.console());
        
    }
}
