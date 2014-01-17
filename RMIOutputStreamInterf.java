import java.rmi.*;
import java.io.IOException;

/**
 * RMIOutputStreamInterf.java
 *
 * Septiembre - Diciembre 2013
 *
 * Interfaz de las funciones de los streams de bytes de salida.
 * Extiende a la clase Remote.
 *
 * Grupo: 42.
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public interface RMIOutputStreamInterf extends Remote {
    
    /**
    * Método que copia en el stream de salida el byte correspondiente
    * al entero que se especifica.
    * 
    * @param b Byte que se va a escribir en el stream de salida
    * @throws IOException En caso de error en la lectura/escritura.
    * @throws RemoteException En caso de error en la llamada remota.
    */
    public void write(int b) throws IOException, RemoteException;
    
    /**
    * Copia al stream de salida una cantidad determinada de bytes desde
    * la posición especificada de un arreglo de bytes dado.
    * 
    * @param b Arreglo del que se copiarán los bytes.
    * @param off Índice a partir del cual se copiarán los bytes en el
    *            stream de salida.
    * @param len Cantidad de bytes que se leerán del arreglo de bytes.
    * @throws IOException en caso de error en la lectura/escritura.
    * @throws RemoteException En caso de error en la llamada remota.
    */
    public void write(byte[] b, int off, int len) throws 
    IOException, RemoteException;
    
    /**
    * Cierra el stream de bytes de salida y libera los recursos del sistema
    * asociados a él.
    * 
    * @throws IOException En caso de error en la lectura/escritura.
    */
    public void close() throws IOException, RemoteException;
}