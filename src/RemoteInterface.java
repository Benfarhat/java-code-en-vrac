

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote {
	
	void printMsg() throws RemoteException;
	String echoMsg(String msg) throws RemoteException;
	
}
