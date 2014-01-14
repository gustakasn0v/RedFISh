import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * RMIFile.java
 *
 * Septiembre - Diciembre 2013
 *
 * Clase correspondiente a los archivos que serán enviados al servidor
 * de archivos mediante el uso de RMI.
 * Extiende a la clase File.
 *
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class RMIFile extends File{
        
        /**
        * Usuario propietario del archivo.
        */
	public static User owner;

	/**
        * Constructor de la clase.
        * 
        * @param filename Nombre del archivo.
        * @param owner Usuario propietario del archivo.
        */
	public RMIFile(String filename,User owner){
		super(filename);
		this.owner = owner;
	}

	/**
        * Constructor de la clase.
        * 
        * @param filename Nombre del archivo.
        */
	public RMIFile(String filename){
		super(filename);
	}

	/**
        * Método para expresar en forma de String la información relacionada
        * a un archivo.
        */
	public String toString(){
		System.out.println(this.getName());
		return "Nombre: "+this.getName() + " | " + this.owner.toString()+"\n";
	}
}