import java.rmi.*;
import java.io.IOException;

/**
 * RMIInputStreamInterf.java
 *
 * Septiembre - Diciembre 2013
 *
 * Interfaz de las funciones de los streams de bytes de entrada.
 * Extiende a la clase Remote.
 *
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public interface RMIInputStreamInterf extends Remote {
    
   /**
   * Lee la cantidad de bytes especificada en el parámetro y lo retorna
   * en un arreglo de bytes.
   * @param len Cantidad de bytes a ser leídos.
   * @return Arreglo con los bytes leídos.
   * @throws IOException En caso de error en la lectura/escritura.
   * @throws RemoteException En caso de error en la llamada remota. 
   */
    public byte[] readBytes(int len) throws IOException, RemoteException;
    
   /**
   * Lee el próximo byte disponible en el stream de entrada
   * @return 
   * @throws IOException En caso de error en la lectura/escritura.
   * @throws RemoteException En caso de error en la llamada remota. 
   */
    public int read() throws IOException, RemoteException;
    
    Closes this input stream and releases any system resources associated with the stream.
    
   /**
   * Cierra el stream de entrada actual y libera los recursos del sistema
   * asociados a él.
   * @throws IOException En caso de error en la lectura/escritura.
   * @throws RemoteException En caso de error en la llamada remota. 
   */
    public void close() throws IOException, RemoteException;
}