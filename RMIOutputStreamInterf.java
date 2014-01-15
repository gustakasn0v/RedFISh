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
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public interface RMIOutputStreamInterf extends Remote {
    
    public void write(int b) throws IOException, RemoteException;
    public void write(byte[] b, int off, int len) throws 
    IOException, RemoteException;
    public void close() throws IOException, RemoteException;
}