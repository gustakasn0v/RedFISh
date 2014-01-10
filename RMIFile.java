import java.io.File;
import java.util.LinkedList;

public class RMIFile extends File{
	private static LinkedList<User> owners;

	public RMIFile(String filename,LinkedList<User> owners){
		super(filename);
		this.owners = owners;
	}
}