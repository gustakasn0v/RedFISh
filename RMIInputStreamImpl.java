import java.io.InputStream;
import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

/**
 * RMIInputStreamImpl.java
 *
 * Septiembre - Diciembre 2013
 *
 * Implementación de las funciones de los streams de bytes de entrada.
 * Implementa a la clase RMIInputStreamInterf.
 *
 * Grupo: 42.
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class RMIInputStreamImpl implements RMIInputStreamInterf {
    
    /**
    * Stream de entrada.
    */
    private InputStream in;
    
    /**
    * Buffer para lectura de bytes.
    */
    private byte[] b;
    
    /**
    * Constructor de la clase.
    * @param in Stream de entrada.
    * @throws IOException En caso de error en la lectura/escritura.
    */
    public RMIInputStreamImpl(InputStream in) throws IOException {
        this.in = in;
        UnicastRemoteObject.exportObject(this, 1099);
    }
    
    /**
    * Cierra el stream de entrada actual y libera los recursos del sistema
    * asociados a él.
    * @throws IOException En caso de error en la lectura/escritura.
    * @throws RemoteException En caso de error en la llamada remota. 
    */
    public void close() throws IOException, RemoteException {
        in.close();
    }
    
    /**
    * Lee el próximo byte disponible en el stream de entrada
    * @return Entero entre 0 y 255 correspondiente al próximo byte del stream.
    * @throws IOException En caso de error en la lectura/escritura.
    * @throws RemoteException En caso de error en la llamada remota. 
    */
    public int read() throws IOException, RemoteException {
        return in.read();
    }
    
   /**
   * Lee la cantidad de bytes especificada en el parámetro y lo retorna
   * en un arreglo de bytes.
   * @param len Cantidad de bytes a ser leídos.
   * @return Arreglo con los bytes leídos.
   * @throws IOException En caso de error en la lectura/escritura.
   * @throws RemoteException En caso de error en la llamada remota. 
   */
    public byte[] readBytes(int len) throws IOException, 
            RemoteException {
        if (b == null || b.length != len)
            b = new byte[len];
            
        int len2 = in.read(b);
        if (len2 < 0)
            return null; // EOF reached
        
        if (len2 != len) {
            // copy bytes to byte[] of correct length and return it
            byte[] b2 = new byte[len2];
            System.arraycopy(b, 0, b2, 0, len2);
            return b2;
        }
        else
            return b;
    }
}