import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanInfoDemo {
	public static void main(String[] args) throws IntrospectionException {
		BeanInfoScanner.scanModel(java.net.Socket.class);
		BeanInfoScanner.scanModel(java.util.Date.class);
	}
}

class BeanInfoScanner {
	public static void scanModel(Class<?> beanClass) throws IntrospectionException {
		BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
	    List<String> fields = new ArrayList<>();
	    List<Method> getters = new ArrayList<>();
	    List<Method> setters = new ArrayList<>();
	
	    for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
	        String name = pd.getName();
	        if (name.equals("class")) {
	            continue;
	        }
	        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
	        String[] s = name.split("(?=\\p{Upper})");
	        String displayName = "";
	        for (String s1 : s) {
	            displayName += s1 + " ";
	        }
	
	        fields.add(displayName);
	        Method mr = pd.getReadMethod();
	        Method mw = pd.getWriteMethod();
	        getters.add(mr);
	        setters.add(mw);
	    }

	    System.out.println("=========================================");
	    System.err.println("Result for " + beanClass.getName());
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
}
