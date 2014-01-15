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
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class RMIOutputStreamImpl implements RMIOutputStreamInterf {
    /**
    * Stream de salida
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
    
    public void write(int b) throws IOException {
        out.write(b);
    }
    public void write(byte[] b, int off, int len) throws 
            IOException {
        out.write(b, off, len);
    }
    public void close() throws IOException {
        out.close();
    }
}