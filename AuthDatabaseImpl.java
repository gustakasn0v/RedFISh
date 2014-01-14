import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * AuthDatabaseImpl.java
 *
 * Septiembre - Diciembre 2013
 *
 * Implementación de la interfaz AuthDatabase, que se utilizará para la
 * autenticación de usuarios.
 * Extiende a la clase UnicastRemoteObject e implementa AuthDatabase.
 *
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class AuthDatabaseImpl extends UnicastRemoteObject implements AuthDatabase {
  
  /**
  * Tabla de hash en la que se almacenan las credenciales de los usuarios.
  * La clave corresponde al nombre de usuario, y el valor es la clave de acceso
  * asociada a ese usuario.
  */
  private Hashtable<String,String> credentialDB;

  /**
   * Constructor de la clase.
   * @throws RemoteException Si no se puede contactar el registro de autenticación.
   */
  public AuthDatabaseImpl() throws RemoteException {
    this.credentialDB = new Hashtable<String,String>();
  }

  /**
   * Método para agregar las credenciales de los usuarios que tienen 
   * acceso al servidor de archivos.
   * 
   * @param user Usuario cuyas credenciales serán agregadas.
   * @return true en caso de que el usuario sea correctamente agregado.
   *         false en caso contrario.
   * @throws RemoteException En caso de error en la llamada remota. 
   */
  public Boolean addUser(User user) throws RemoteException{
    if ( this.credentialDB.get(user.username) != null) return false;
    else{
      this.credentialDB.put(user.username,user.password);
      return true;
    }
  }

  /**
   * Valida que un usuario pertenezca a los usuarios con acceso al 
   * servidor de archivos.
   * 
   * @param user Usuario cuyas credenciales serán validadas.
   * @return Lista con los usuarios cuyas credenciales son correctas.
   * @throws RemoteException En caso de error en la llamada remota. 
   */
  public LinkedList<User> authenticate(LinkedList<User> credentials) throws RemoteException{
    LinkedList<User> authenticated = new LinkedList<User>();
    for(User user : credentials){
      if ((this.credentialDB.get(user.username) != null 
        && this.credentialDB.get(user.username).equals(user.password))){
        authenticated.add(user);
      }
      
    }
    return authenticated;
  }
  
}
