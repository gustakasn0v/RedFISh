import java.rmi.*;
import java.util.LinkedList;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;


// Hay que ponerle excepciones a esto!
/**
 * FileServer.java
 *
 * Septiembre - Diciembre 2013
 *
 * Interfaz de las funciones del servidor de archivos.
 * Extiende a la interfaz Remote.
 *
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public interface FileServer extends Remote{

        /**
        * Lista los archivos existentes en el servidor de archivos.
        * 
        * @param user Usuario que ejecuta el comando correspondiente a listar
        *             los archivos del servidor de archivos.
        * @return String con los nombres de los archivos existentes en el servidor.
        * @throws RemoteException En caso de que ocurra algún error en la llamada remota.
        * @throws NotAuthenticatedException Si el usuario no está autenticado
        */
	public String listFiles(User user) throws RemoteException,NotAuthenticatedException;

	/**
        * Valida la autenticación de un usuario.
        * 
        * @param user Usuario cuyas credenciales serán validadas.
        * @return true en caso de que las credenciales sean correctas, 
                  false en caso contrario.
        * @throws RemoteException En caso de error en la llamada remota. 
        */
	public Boolean testUser(User user) throws RemoteException;

	public InputStream getInputStream(File f, User user) throws IOException,RemoteException,NotAuthenticatedException;

	public OutputStream getOutputStream(File f,User owner) throws IOException,RemoteException,NotAuthenticatedException;

	/**
        * Dado el nombre de un archivo y las credenciales de un usuario, el
        * archivo es eliminado si el usuario es su propietario.
        * 
        * @param src Nombre del archivo que se desea eliminar.
        * @param credentials Credenciales del usuario que ejecuta el comando
                             de eliminación de archivo.
        * @throws RemoteException En caso de que ocurra algún error en la llamada remota.
        * @throws NotAuthorizedException Si el usuario no está autorizado para eliminar el archivo.
        * @throws FileNotFoundException En caso de que el nombre del archivo no exista.
        */
	public void deleteFile(String src, User credentials) throws RemoteException,NotAuthorizedException,FileNotFoundException;	
}