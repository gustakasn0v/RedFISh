import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;

public class RMIFile extends File implements Serializable{
	public static User owner;

	public RMIFile(String filename,User owner){
		super(filename);
		this.owner = owner;
	}

	public RMIFile(String filename){
		super(filename);
	}
}