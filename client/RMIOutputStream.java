import java.io.IOException;
import java.io.Serializable;
import java.io.OutputStream;

/**
 * RMIOutputStream.java
 *
 * Septiembre - Diciembre 2013
 *
 * DESCRIPCION DE LA CLASE.
 * Extiende a la clase OutputStream e implementa Serializable.
 *
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class RMIOutputStream extends OutputStream implements 
        Serializable {
    private RMIOutputStreamInterf out;
    
    public RMIOutputStream(RMIOutputStreamInterf out) {
        this.out = out;
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