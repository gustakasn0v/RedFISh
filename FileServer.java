import java.rmi.*;
import java.util.LinkedList;


// Hay que ponerle excepciones a esto!

public interface FileServer extends Remote{
	public LinkedList<RMIFile> listFiles();

	public Boolean uploadFile(RMIFile source, RMIFile destination, LinkedList<User> credentials);

	public Boolean downloadFile(RMIFile source, RMIFile destination);

	public Boolean deleteFile(RMIFile source, LinkedList<User> credentials);	
}