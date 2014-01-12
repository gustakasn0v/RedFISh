import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.LinkedList;

/** This is the actual implementation of Rem that
 *  the RMI server uses. The server builds an instance
 *  of this then registers it with a URL. The
 *  client accesses the URL and binds the result to
 *  a Rem (not a RemImpl; it doesn't have this).
 */

public class AuthDatabaseImpl extends UnicastRemoteObject implements AuthDatabase {
  private Hashtable<String,String> credentialDB;

  public AuthDatabaseImpl() throws RemoteException {
    this.credentialDB = new Hashtable<String,String>();
  }


  public Boolean addUser(User user) throws RemoteException{
    if ( this.credentialDB.get(user.username) != null) return false;
    else{
      this.credentialDB.put(user.username,user.password);
      return true;
    }
  }

  public LinkedList<User> authenticate(LinkedList<User> credentials) throws RemoteException{
    LinkedList<User> authenticated = new LinkedList<User>();
    System.out.println(""+credentials.size());
    for(User user : credentials){
      if ((this.credentialDB.get(user.username) != null 
        && this.credentialDB.get(user.username).equals(user.password))){
        authenticated.add(user);
      }
      
    }
    return authenticated;
  }
  
}
