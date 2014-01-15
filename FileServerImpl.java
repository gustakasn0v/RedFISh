import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.LinkedList;
import java.rmi.registry.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;

import org.apache.commons.collections4.queue.CircularFifoQueue;

/**
 * FileServerImpl.java
 *
 * Septiembre - Diciembre 2013
 *
 * Implementación de las funciones del servidor de archivos.
 *
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class FileServerImpl extends UnicastRemoteObject implements FileServer{
	
	/**
	* Tabla de hash donde la clave es el nombre del archivo, y el valor
	* el objeto correspondiente al archivo con ese nombre.
	*/
	private Hashtable<String,RMIFile> serverFiles;

	/**
        * Base de datos para la autenticación de los usuarios.
        */
	private AuthDatabase authServer;

	/**
        * Cola circular en la que se almacena el historial con los comandos 
        * enviados por los clientes al servidor de archivos.
        */
	private CircularFifoQueue<FileServerCommand> history;

	/**
        * Tamaño de la cola donde se almacenan los comandos del historial.
        */
	private static Integer HIST_SIZE = 20;

	private void addCWDFilesToServer() {
		File folder = new File(".");
	    for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	            RMIFile newFile = new RMIFile(
	            	fileEntry.getName(),
	            	new User("servidor","servidor")
	         	);
				this.serverFiles.put(fileEntry.getName(),newFile);
	        }
	    }
	}

        /**
        * Constructor de la clase.
        * 
        * @param authHost Host del servidor de autenticación.
        * @param authPort Puerto del servidor de autenticación.
        * @throws RemoteException Si no se puede contactar el registro de autenticación.
        * @throws NotBoundException Si no se puede obtener el objeto de autenticación.
        */
        public FileServerImpl(String authHost, int authPort) throws RemoteException,NotBoundException{
                try{
                        this.serverFiles = new Hashtable<String,RMIFile>();
                        this.addCWDFilesToServer();

                        //Creo la conexion al servidor de autenticacion
                        Registry registry = LocateRegistry.getRegistry(authHost,authPort);
                    this.authServer = (AuthDatabase)registry.lookup("Auth");

                    this.history = new CircularFifoQueue<FileServerCommand>(this.HIST_SIZE);
                }
                catch(RemoteException re){
                        throw new RemoteException("Imposible contactar registro de autenticación",re);
                }
                catch(NotBoundException nbe){
                        throw new RemoteException("Imposible obtener objeto de autenticación",nbe);
                }
                
                
        }
        
        /**
        * Lista los últimos 20 comandos que los clientes han enviado
        * al servidor.
        *
        * @return String con los últimos 20 comandos que han sido enviados
        *         al servidor. Incluye el nombre del comando, el argumento
        *         en caso de que aplique, y el usuario que lo ejecutó.
        */
	public String getHistory(){
		return this.history.toString();
	}

	/**
        * Valida la autenticación de un usuario.
        * 
        * @param user Usuario cuyas credenciales serán validadas.
        * @return true en caso de que las credenciales sean correctas, 
        *         false en caso contrario.
        * @throws RemoteException En caso de error en la llamada remota. 
        */
	public Boolean testUser(User user) throws RemoteException{
		try{
			authenticate(user);
			return true;
		}
		catch(NotAuthenticatedException ne){
			return false;
		}

	}

        /**
        * Autentica a un usuario.
        * 
        * @param user Usuario a autenticar.
        * @throws RemoteException En caso de error en la llamada remota.
        * @throws NotAuthenticatedException En caso de que las credenciales del
        *         usuario sean inválidas.
        */
	private void authenticate(User user) throws NotAuthenticatedException,RemoteException{
		try{
			LinkedList<User> credentials = new LinkedList<User>();
			credentials.add(user);
			if (this.authServer.authenticate(credentials).size() == 0)
				throw new NotAuthenticatedException();
		}
		catch (RemoteException re){
			throw new RemoteException("Error autenticando al usuario");
		}
		
	}

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
	public InputStream getInputStream(File f,User user) throws IOException,RemoteException,NotAuthenticatedException {
		authenticate(user);
		this.history.add(
			new FileServerCommand("baj",f.getName(),user)
		);
                return new RMIInputStream(new RMIInputStreamImpl(new 
                    FileInputStream(f)));
	}

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
	public OutputStream getOutputStream(File f,User owner) throws IOException, FileExistsException,
	RemoteException,NotAuthenticatedException {
		authenticate(owner);
		if (this.serverFiles.get(f.getName()) != null ) throw new FileExistsException();
		RMIFile newFile = new RMIFile(f.getName(),new User(owner.username,owner.password));
		
		serverFiles.put(f.getName(),newFile);

		this.history.add(
			new FileServerCommand("sub",f.getName(),owner)
		);

	    return new RMIOutputStream(new RMIOutputStreamImpl(new FileOutputStream(f)));
	}

	/**
        * Lista los archivos existentes en el servidor de archivos.
        * 
        * @param user Usuario que ejecuta el comando correspondiente a listar
        *             los archivos del servidor de archivos.
        * @return String con los nombres de los archivos existentes en el servidor.
        * @throws RemoteException En caso de que ocurra algún error en la llamada remota.
        * @throws NotAuthenticatedException Si el usuario no está autenticado
        */
	public String listFiles(User user) throws RemoteException,NotAuthenticatedException{
		authenticate(user);
		this.history.add(
			new FileServerCommand("rls","",user)
		);
		LinkedList<RMIFile> listOfFiles = new LinkedList<RMIFile>(this.serverFiles.values());
		return listOfFiles.toString();
	}

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
	public void deleteFile(String src, User credentials) throws RemoteException,NotAuthorizedException,FileNotFoundException, NotAuthenticatedException{
		authenticate(credentials);
		if (credentials.equals(this.serverFiles.get(src).owner)){
			File toDelete = new File(src);

			if (!toDelete.exists()) throw new FileNotFoundException();

			this.history.add(
				new FileServerCommand("bor",src,credentials)
			);

			System.out.println(toDelete.delete());
		}
		else throw new NotAuthorizedException();	
	}
}