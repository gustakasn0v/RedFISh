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

public class FileServerImpl extends UnicastRemoteObject implements FileServer{
	private Hashtable<String,RMIFile> serverFiles;

	private AuthDatabase authServer;

	private static Integer BUF_SIZE = 2048;

	public FileServerImpl(String authHost, int authPort) throws RemoteException{
		try{
			// No tengo idea que agregar a la lista de archivos del servidor
			this.serverFiles = new Hashtable<String,RMIFile>();

			//Creo la conexion al servidor de autenticacion
			Registry registry = LocateRegistry.getRegistry(authHost,authPort);
		    this.authServer = (AuthDatabase)registry.lookup("Auth");
		}

		catch(RemoteException re){
			System.out.println("Imposible contactar al servidor de autenticación");
			re.printStackTrace();
		}

		catch(NotBoundException nbe){
			System.out.println("Imposible obtener objeto remoto de autenticación");
			nbe.printStackTrace();
		}
		
	}

	private void authenticate(User user) throws NotAuthenticatedException,RemoteException{
		LinkedList<User> credentials = new LinkedList<User>();
		credentials.add(user);
		if (this.authServer.authenticate(credentials).size() == 0)
			throw new NotAuthenticatedException();
	}

	public InputStream getInputStream(File f,User user) throws IOException,RemoteException,NotAuthenticatedException {
		authenticate(user);
    	return new RMIInputStream(new RMIInputStreamImpl(new 
    		FileInputStream(f)));
	}

	public OutputStream getOutputStream(File f,User owner) throws IOException,RemoteException,NotAuthenticatedException {
		authenticate(owner);
		RMIFile newFile = new RMIFile(f.getName(),owner);
		//System.out.println(newFile.getName());
		serverFiles.put(f.getName(),newFile);
	    return new RMIOutputStream(new RMIOutputStreamImpl(new FileOutputStream(f)));
	}

	public LinkedList<RMIFile> listFiles() throws RemoteException{return null;}

	public void deleteFile(String src, User credentials) throws RemoteException,NotAuthorizedException{
		if (credentials.equals(this.serverFiles.get(src).owner)){
			File toDelete = new File(src);
			toDelete.delete();
		}
		else throw new NotAuthorizedException();	
	}
}