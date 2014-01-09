import java.rmi.*; // For Naming, RemoteException, etc.
import java.net.*; // For MalformedURLException
import java.io.*;  // For Serializable interface
import java.rmi.registry.*;

/** Get a Rem object from the specified remote host.
 *  Use its methods as though it were a local object.
 * @see Rem
 */

public class RemClient {
  public static void main(String[] args) {
    try {
      String host =
        (args.length > 0) ? args[0] : "localhost";
        
      String port =
        (args.length > 0) ? args[1] : "20226";
        
      Registry registry=LocateRegistry.getRegistry(
               host,
               (new Integer(port)).intValue()
      );
      // Get remote object and store it in remObject:
      Rem remObject =
        (Rem)registry.lookup("Rem");
      // Call methods in remObject:
      System.out.println(remObject.getMessage("holaa"));
    }
    catch(NullPointerException e){
      System.out.println("WEBOOOOO");
    }
    catch(RemoteException re) {
      System.out.println("RemoteException: " + re);
    }
    catch(NotBoundException nbe) {
      System.out.println("NotBoundException: " + nbe);
    }
//    catch(MalformedURLException mfe) {
//      System.out.println("MalformedURLException: "
//                         + mfe);
  //  }
  }
}
