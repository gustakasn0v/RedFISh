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

public class FileServerImpl extends UnicastRemoteObject implements FileServer{
	private Hashtable<String,RMIFile> serverFiles;

	private AuthDatabase authServer;

	private CircularFifoQueue<FileServerCommand> history;

	private static Integer BUF_SIZE = 2048;

	private static Integer HIST_SIZE = 20;

	public String getHistory(){
		return this.history.toString();
	}

	public Boolean testUser(User user) throws RemoteException{
		try{
			authenticate(user);
			return true;
		}
		catch(NotAuthenticatedException ne){
			return false;
		}

	}

	public FileServerImpl(String authHost, int authPort) throws RemoteException,NotBoundException{
		try{
			// No tengo idea que agregar a la lista de archivos del servidor
			this.serverFiles = new Hashtable<String,RMIFile>();

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

	public InputStream getInputStream(File f,User user) throws IOException,RemoteException,NotAuthenticatedException {
		authenticate(user);
		this.history.add(
			new FileServerCommand("baj",f.getName(),user)
		);
    	return new RMIInputStream(new RMIInputStreamImpl(new 
    		FileInputStream(f)));
	}

	public OutputStream getOutputStream(File f,User owner) throws IOException,RemoteException,NotAuthenticatedException {
		authenticate(owner);
		RMIFile newFile = new RMIFile(f.getName(),new User(owner.username,owner.password));
		
		serverFiles.put(f.getName(),newFile);

		this.history.add(
			new FileServerCommand("sub",f.getName(),owner)
		);

	    return new RMIOutputStream(new RMIOutputStreamImpl(new FileOutputStream(f)));
	}

	public String listFiles(User user) throws RemoteException,NotAuthenticatedException{
		authenticate(user);
		this.history.add(
			new FileServerCommand("rls","",user)
		);
		LinkedList<RMIFile> listOfFiles = new LinkedList<RMIFile>(this.serverFiles.values());
		return listOfFiles.toString();
	}

	public void deleteFile(String src, User credentials) throws RemoteException,NotAuthorizedException,FileNotFoundException{
		if (credentials.equals(this.serverFiles.get(src).owner)){
			File toDelete = new File(src);

			if (!toDelete.exists()) throw new FileNotFoundException();

			this.history.add(
				new FileServerCommand("bor",src,credentials)
			);

			toDelete.delete();
		}
		else throw new NotAuthorizedException();	
	}
}