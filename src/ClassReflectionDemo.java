import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassReflectionDemo {

	public static void main(String[] args) throws IntrospectionException {
		ClassReflectionScanner.scanModel(java.net.Socket.class);
		ClassReflectionScanner.scanModel(java.util.Date.class);
	}
}

class ClassReflectionScanner {
	public static void scanModel(Class<?> inputClass) throws IntrospectionException {

	    List<Field> properties = new ArrayList<>();
	    List<String> fields = new ArrayList<>();
	    List<Method> methods = new ArrayList<>();
	    List<Method> getters = new ArrayList<>();
	    List<Method> setters = new ArrayList<>();
	    
	    properties = Arrays.asList(inputClass.getDeclaredFields());
	    methods = Arrays.asList(inputClass.getMethods());
	    
	    for(Field p : properties) {
	        String name = p.getName();
	        if (name.equals("class")) {
	            continue;
	        }
	        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
	        String[] s = name.split("(?=\\p{Upper})");
	        String displayName = "";
	        for (String s1 : s) {
	            displayName += s1 + " ";
	        }
	        displayName += "(" + p.getType().getName() + ")";
	        fields.add(displayName);
	    }
	    
	    for(Method m : methods) {
	        if(isGetter(m)) getters.add(m);
	        if(isSetter(m)) setters.add(m);
	    }
	    
	    System.out.println("=========================================");
	    System.err.println("Result for " + inputClass.getName());
	    System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
	    System.out.println("Fields list");
	    System.out.println(".........................................");
	    fields.forEach(System.out::println);
	    System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
	    System.out.println("Getters list");
	    System.out.println(".........................................");
	    getters.forEach(System.out::println);
	    System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
	    System.out.println("Setters list");
	    System.out.println(".........................................");
	    setters.forEach(System.out::println);
	    System.out.println("=========================================");
	     
	}
	

	private static boolean isGetter(Method method){
	  if(!method.getName().startsWith("get"))      return false;
	  if(method.getParameterTypes().length != 0)   return false;  
	  if(void.class.equals(method.getReturnType())) return false;
	  return true;
	}
	
	private static boolean isSetter(Method method){
	  if(!method.getName().startsWith("set")) return false;
	  if(method.getParameterTypes().length != 1) return false;
	  return true;
	}
}
