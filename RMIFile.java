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
 * Grupo: 42.
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class RMIFile implements Serializable{
        
        /**
        * Usuario propietario del archivo.
        */
	public String owner;

        /**
        * Nombre del archivo.
        */

	public String filename;

	/**
        * Constructor de la clase.
        * 
        * @param filename Nombre del archivo.
        * @param owner Usuario propietario del archivo.
        */
	public RMIFile(String filename,String owner){
		this.filename = filename;
		this.owner = owner;
	}

	/**
        * Método para expresar en forma de String la información relacionada
        * a un archivo.
        *
        * @return String con la información asociada a un archivo.
        */
	public String toString(){
		if (this.owner == null)
		  return "Nombre: "+this.filename + " | " + "Usuario: Fileserver\n";
		return "Nombre: "+this.filename + " | " + "Usuario: " + this.owner + "\n";
	}
}
