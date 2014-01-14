/**
 * FileExistsException.java
 *
 * Septiembre - Diciembre 2013
 *
 * Implementación de excepción utilizada en caso de que un 
 * usuario intente subir un archivo que ya existe en el
 * servidor de archivos.
 * Extiende a la clase Exception.
 *
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class FileExistsException extends Exception {

    /**
    * Constructor sin parámetros de la clase.
    */
    public FileExistsException(){
        super();
    }

    /**
    * Constructor de la clase que recibe un string como parámetro.
    * 
    * @param s Mensaje que será mostrado si ocurre la excepción.
    */
    public FileExistsException(String s){
        super(s);
    }
}    