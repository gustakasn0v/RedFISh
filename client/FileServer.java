import java.rmi.*;
import java.util.LinkedList;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;


// Hay que ponerle excepciones a esto!

public interface FileServer extends Remote{

	public LinkedList<RMIFile> listFiles() throws RemoteException;

	public InputStream getInputStream(File f, User user) throws IOException,RemoteException,NotAuthenticatedException;

	public OutputStream getOutputStream(File f,User owner) throws IOException,RemoteException,NotAuthenticatedException;

	public void deleteFile(String src, User credentials) throws RemoteException,NotAuthorizedException;	
}