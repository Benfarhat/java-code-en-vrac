import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

public class RemoteClient {

	private static RemoteInterface lookup;

	public static void main(String[] args) 
		throws MalformedURLException, RemoteException, NotBoundException {
		
		lookup = (RemoteInterface) Naming.lookup("//localhost/MyServer");
		
		String txt = JOptionPane.showInputDialog("Give me a sentence?");
			
		lookup.printMsg();
		String response = lookup.echoMsg(txt);
		JOptionPane.showMessageDialog(null, "Response from RemoteServer: " + response);

	}

}