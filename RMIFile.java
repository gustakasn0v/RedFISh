import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;

public class RMIFile extends File{
	public static User owner;

	public RMIFile(String filename,User owner){
		super(filename);
		this.owner = owner;
	}

	public RMIFile(String filename){
		super(filename);
	}

	public String toString(){
		System.out.println(this.getName());
		return "Nombre: "+this.getName() + " | " + this.owner.toString();
	}
}