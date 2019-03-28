import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteServer extends UnicastRemoteObject implements RemoteInterface{

    private static final long serialVersionUID = 1L;

    protected RemoteServer() throws RemoteException {
        super();
    }
    @Override
    public String echoMsg(String msg) throws RemoteException{
    	StringBuilder result = new StringBuilder();
    	String[] stringArray = msg.split(" ");
        for(int i = 0; i<stringArray.length; i++){
            String temp = new StringBuilder(stringArray[i]).reverse().toString();
            result.append(temp+" ");
        }
        return result.toString();
    }
	@Override
	public void printMsg() throws RemoteException {		
		System.out.println("Hello from RMI Server!!!");		
	}

    public static void main(String[] args){

        try {

        	System.err.println("Remember to start rmiregistry before starting your server?\n\n\t$> start rmiregistry\n\n");
            Naming.rebind("//localhost/MyServer", new RemoteServer());            
            System.err.println("Server ready");

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();

        }

    }


}