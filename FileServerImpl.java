import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.rmi.registry.*;

public class FileServerImpl extends UnicastRemoteObject implements FileServer{
	private LinkedList<RMIFile> serverFiles;

	private AuthDatabase authServer;

	public FileServerImpl(String authHost, int authPort) throws RemoteException{
		try{
			// No tengo idea que agregar a la lista de archivos del servidor
			this.serverFiles = new LinkedList<RMIFile>();

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

	public LinkedList<RMIFile> listFiles(){return null;}

	public Boolean uploadFile(RMIFile source, RMIFile destination, LinkedList<User> credentials){return true;}
	public Boolean downloadFile(RMIFile source, RMIFile destination){return true;}

	public Boolean deleteFile(RMIFile source, LinkedList<User> credentials){return true;}
}