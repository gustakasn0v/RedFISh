import java.rmi.*;
import java.util.LinkedList;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;

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
        * @throws NotAuthenticatedException Si el usuario no está autenticado.
        */
	public String listFiles(User user) throws RemoteException,NotAuthenticatedException;

	/**
        * Valida la autenticación de un usuario.
        * 
        * @param user Usuario cuyas credenciales serán validadas.
        * @return true en caso de que las credenciales sean correctas, 
        *         false en caso contrario.
        * @throws RemoteException En caso de error en la llamada remota. 
        */
	public Boolean testUser(User user) throws RemoteException;

	/**
        * Dado un archivo y un usuario que desea descargar del servidor de
        * archivos, se retorna el stream de entrada correspondiente a los bytes
        * del archivo.
        *
        * @param f Archivo que se desea descargar del servidor.
        * @param user Usuario que desea descargar el archivo del servidor.
        * @return Stream de entrada que contiene los bytes del archivo.
        * @throws IOException En caso de error en la lectura/escritura.
        * @throws RemoteException En caso de error en la llamada remota. 
        * @throws NotAuthenticatedException En caso de que el usuario no esté
        *          autenticado.
        */
	public InputStream getInputStream(File f, User user) throws IOException,RemoteException,NotAuthenticatedException;

	/**
        * Dado un archivo y un usuario que lo sube al servidor de archivos,
        * se retorna el stream de salida correspondiente a los bytes del archivo.
        *
        * @param f Archivo que se desea subir al servidor.
        * @param owner Usuario que desea subir el archivo al servidor.
        * @return Stream de salida que contiene los bytes del archivo.
        * @throws IOException En caso de error en la lectura/escritura.
        * @throws RemoteException En caso de error en la llamada remota. 
        * @throws NotAuthenticatedException En caso de que el usuario no esté
        *          autenticado.        
        * @throws FileExistsException En caso de que el archivo a subir ya
        *         exista en el servidor de archivos.
        */
	public OutputStream getOutputStream(File f,User owner) throws IOException,RemoteException,NotAuthenticatedException,FileExistsException;

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
        * @throws NotAuthenticatedException Si el usuario no está autenticado.
        */
        public void deleteFile(String src, User credentials) throws RemoteException,NotAuthorizedException,FileNotFoundException, NotAuthenticatedException;
}