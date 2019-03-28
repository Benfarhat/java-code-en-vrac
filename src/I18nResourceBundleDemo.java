import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18nResourceBundleDemo {

	public static void main(String[] args) {
		// from properties files
		System.out.println("FROM PROPERTIES FILE");
		
		System.out.println("Current Locale: " + Locale.getDefault());
		ResourceBundle mybundle = ResourceBundle.getBundle("I18nResourceBundleDemo_Welcome");
		
		System.out.println("-:=:-:=-:=-:=-:=-:=-:=-=-:=-=:=-:=-");
		

		Locale locale = new Locale("ar", "TN");
		mybundle = ResourceBundle.getBundle("I18nResourceBundleDemo_Welcome", locale);
		System.out.println(mybundle.getString("welcome"));
		
		locale = new Locale("fr", "CA");
		mybundle = ResourceBundle.getBundle("I18nResourceBundleDemo_Welcome", locale);
		System.out.println(mybundle.getString("welcome"));
		
		locale = new Locale("en", "US");
		mybundle = ResourceBundle.getBundle("I18nResourceBundleDemo_Welcome", locale);
		System.out.println(mybundle.getString("welcome"));

				
		locale = new Locale("ar", "TN");
		mybundle = ResourceBundle.getBundle("I18nResourceBundleDemo_Welcome", locale);
		System.out.println("Say check your messages in arabic: " + mybundle.getString("check_your_messages"));

		locale = new Locale("fr", "CA");
		mybundle = ResourceBundle.getBundle("I18nResourceBundleDemo_Welcome", locale);
		System.out.println("Say log to your account in french: " + mybundle.getString("login_to_you_account"));
		
		locale = new Locale("en", "US");
		mybundle = ResourceBundle.getBundle("I18nResourceBundleDemo_Welcome", locale);
		System.out.println("Say diconnect in english: " + mybundle.getString("disconnect"));	
		
		Locale.setDefault(new Locale("de", "DE"));

	
		System.out.println("Current Locale: " + Locale.getDefault());

		
		locale = new Locale("jp", "JP");
		mybundle = ResourceBundle.getBundle("I18nResourceBundleDemo_Welcome", locale);
		System.out.println("Say diconnect in jp / JP (properties files is not defined) wrong locale: " + mybundle.getString("disconnect"));

		
		// From List Resource Bundle Class
		System.out.println("FROM LISTRESOURCEBUNDLE CLASS");

		locale = new Locale("jp", "JP"); 
		ResourceBundle bundle = ResourceBundle.getBundle("i18n.MyClassBundle", locale);

		System.out.println("salary   : " + bundle.getObject("salary"));
		System.out.println("hello: " + bundle.getObject("hello"));
		System.out.println("goodbye: " + bundle.getObject("goodbye"));

		locale = new Locale("es", "ES"); 
		bundle = ResourceBundle.getBundle("i18n.MyClassBundle", locale);

		System.out.println("salary   : " + bundle.getObject("salary"));
		System.out.println("hello: " + bundle.getObject("hello"));
		System.out.println("goodbye: " + bundle.getObject("goodbye"));
		
		locale = new Locale("de", "DE"); 
		bundle = ResourceBundle.getBundle("i18n.MyClassBundle", locale);

		System.out.println("salary   : " + bundle.getObject("salary"));
		System.out.println("hello: " + bundle.getObject("hello"));
		System.out.println("goodbye: " + bundle.getObject("goodbye"));



	}

}
