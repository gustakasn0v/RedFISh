import java.io.IOException;
import java.io.Serializable;
import java.io.InputStream;

/**
 * RMIInputStream.java
 *
 * Septiembre - Diciembre 2013
 *
 * Clase de los stream de bytes de entrada para RMI.
 * Extiende a la clase InputStream e implementa la interfaz Serializable.
 *
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class RMIInputStream extends InputStream implements 
        Serializable {
        
    /**
    * Stream de entrada de bytes por RMI. 
    */
    RMIInputStreamInterf in;
    
    /**
    * Constructor de la clase.
    *
    * @param in Stream de entrada con el que se inicializará.
    */
    public RMIInputStream(RMIInputStreamInterf in) {
        this.in = in;
    }
    
    /**
    * Lee el próximo byte disponible en el stream de entrada.
    * 
    * @return Entero entre 0 y 255 correspondiente al próximo byte del stream.
    * @throws IOException en caso de error en la lectura/escritura.
    */
    public int read() throws IOException {
        return in.read();
    }
    
    /**
    * Lee una cantidad de bytes desde el stream de entrada hasta
    * un arreglo de bytes.
    * 
    * @param b Arreglo en donde se copiarán los bytes.
    * @param off Índice a partir del cual se copiarán los bytes en el
    *            arreglo destino.
    * @param len Cantidad de bytes que se leerán del stream de entrada.
    * @return Entero con la cantidad de bytes leídos.
    * @throws IOException en caso de error en la lectura/escritura.
    */
    public int read(byte[] b, int off, int len) throws IOException {
        byte[] b2 = in.readBytes(len);
        if (b2 == null)
            return -1;
        int i = b2.length;
        System.arraycopy(b2, 0, b, off, i);
        return i;
    }
    
    /**
    * Cierra el stream de bytes de entrada y libera los recursos del sistema
    * asociados a él.
    * 
    * @throws IOException en caso de error en la lectura/escritura.
    */
    public void close() throws IOException {
        super.close();
    }
    
}