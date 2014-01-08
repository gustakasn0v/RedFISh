import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

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


  public Boolean addUser(User user){
    if ( this.credentialDB.get(user.username) != null) return false;
    else{
      this.credentialDB.put(user.username,user.password);
      return true;
    }
  }

  public Boolean authenticate(String username, String password){
    String pass = this.credentialDB.get(username);
    return (pass != null && pass == password);
  }
  
}
