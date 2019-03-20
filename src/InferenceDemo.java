import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class InferenceDemo {
    public static int localVar = 10;
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        // var value1; // Cannot use 'var' on variable without initializer
        // var value2 = null; // Cannot infer type for local variable initialized to 'null'
        var value3 = returnString(); // it works, inference to java.lang.String
        var value4 = 10; // it works, occurence of int
        var value5 = localVar; // it works, occurence of int
        //var value6 = this::localVar; // Method reference needs an explicit target-type
        // var value7 = {1, 2}; // Array initializer needs an explicit target-type
        
        // The constructor Boolean(boolean) is deprecated since version 9
        //var value8 = new Boolean(true); // it works, inference to java.lang.Boolean
        
        var value8 = Boolean.valueOf(true);
        var value9 = new ArrayList<Integer>(); // it works, inference to java.util.ArrayList<Integer>
        var value10 = String.class.getName(); // it works, inference to java.lang.String      
        var value11 = System.getenv(); // it works, inference to java.util.Map<String, String>
        try (var value12 = DriverManager.getConnection("jdbc:mysql://localhost")) { // it works, inference to java.sql.Connection
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        testVar(1);
        testVar('1');
        testVar("1");
    }
    public static String returnString() {
        return "String";
    }
    public static void testVar(Object valueA) {
        var valueB = valueA; // it works
        if(int.class.isInstance(valueA)) {
            System.out.println("I am an integer");
        }
        if(char.class.isInstance(valueA)) {
            System.out.println("I am a character");
        }
        if(valueB instanceof java.lang.String) {
            System.out.println("I am a String");
        }
    }
}
