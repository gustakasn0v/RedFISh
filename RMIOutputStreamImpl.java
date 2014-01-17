import java.io.OutputStream;
import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;

/**
 * RMIOutputStreamImpl.java
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
public class RMIOutputStreamImpl implements RMIOutputStreamInterf {
    
    /**
    * Stream de salida.
    */
    private OutputStream out;
    
    /**
    * Constructor de la clase.
    * @param out Stream de salida.
    * @throws IOException En caso de error en la lectura/escritura.
    */
    public RMIOutputStreamImpl(OutputStream out) throws 
            IOException {
        this.out = out;
        UnicastRemoteObject.exportObject(this, 1099);
    }
    
    /**
    * Método que copia en el stream de salida el byte correspondiente
    * al entero que se especifica.
    * 
    * @param b Byte que se va a escribir en el stream de salida
    * @throws IOException En caso de error en la lectura/escritura.
    * @throws RemoteException En caso de error en la llamada remota.
    */
    public void write(int b) throws IOException {
        out.write(b);
    }
    
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
            IOException {
        out.write(b, off, len);
    }
    
    
    /**
    * Cierra el stream de bytes de salida y libera los recursos del sistema
    * asociados a él.
    * 
    * @throws IOException En caso de error en la lectura/escritura.
    */
    public void close() throws IOException {
        out.close();
    }
}