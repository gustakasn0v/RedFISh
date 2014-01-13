import java.rmi.*;
import java.io.IOException;

/**
 * RMIOutputStreamInterf.java
 *
 * Septiembre - Diciembre 2013
 *
 * DESCRIPCION DE LA CLASE.
 * Extiende a la interfaz Remote.
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