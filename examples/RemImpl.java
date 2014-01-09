import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** This is the actual implementation of Rem that
 *  the RMI server uses. The server builds an instance
 *  of this then registers it with a URL. The
 *  client accesses the URL and binds the result to
 *  a Rem (not a RemImpl; it doesn't have this).
 */

public class RemImpl extends UnicastRemoteObject implements Rem {
  public RemImpl() throws RemoteException {}

  public String getMessage(String blah) throws RemoteException,NullPointerException {
    System.out.println("Enter something here : ");
    try{
    	BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
    	String s = bufferRead.readLine();
      throw new NullPointerException();
    }
    catch(IOException e){
	e.printStackTrace();
	return null;
    }
  

  }
}
