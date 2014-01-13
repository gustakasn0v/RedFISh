/**
 * NotAuthorizedException.java
 *
 * Septiembre - Diciembre 2013
 *
 * Implementación de excepción utilizada en caso de que un 
 * usuario no esté autorizado para ejecutar una acción en el
 * servidor de archivos.
 * Extiende a la clase Exception.
 *
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class NotAuthorizedException extends Exception {

    /**
    * Constructor sin parámetros de la clase.
    */
    public NotAuthorizedException(){
        super();
    }

    /**
    * Constructor de la clase que recibe un string como parámetro.
    * 
    * @param s Mensaje que será mostrado si ocurre la excepción.
    */
    public NotAuthorizedException(String s){
        super(s);
    }
}    