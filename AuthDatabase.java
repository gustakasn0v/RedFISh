import java.rmi.*;
import java.util.LinkedList;

/**
 * AuthDatabase.java
 *
 * Septiembre - Diciembre 2013
 *
 * Interfaz de la clase que se utilizará para la autenticación de los usuarios.
 * Extiende a la interfaz Remote.
 *
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public interface AuthDatabase extends Remote {

        /**
        * Método para agregar las credenciales de los usuarios que tienen 
        * acceso al servidor de archivos.
        * 
        * @param user Usuario cuyas credenciales serán agregadas.
        * @return true en caso de que el usuario sea correctamente agregado.
        *         false en caso contrario.
        * @throws RemoteException En caso de error en la llamada remota. 
        */
	public Boolean addUser(User user) throws RemoteException;

	/**
        * Valida que un usuario pertenezca a los usuarios con acceso al 
        * servidor de archivos.
        * 
        * @param user Usuario cuyas credenciales serán validadas.
        * @return Lista con los usuarios cuyas credenciales son correctas.
        * @throws RemoteException En caso de error en la llamada remota. 
        */
	public LinkedList<User> authenticate(LinkedList<User> credentials) throws RemoteException;
}
