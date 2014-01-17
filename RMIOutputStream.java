import java.io.IOException;
import java.io.Serializable;
import java.io.OutputStream;

/**
 * RMIOutputStream.java
 *
 * Septiembre - Diciembre 2013
 *
 * Clase de los stream de bytes de salida para RMI.
 * Extiende a la clase OutputStream e implementa la interfaz Serializable.
 *
 * Grupo: 42.
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class RMIOutputStream extends OutputStream implements 
        Serializable {
    
    /**
    * Stream de salida de bytes por RMI. 
    */
    private RMIOutputStreamInterf out;
    
    /**
    * Constructor de la clase.
    *
    * @param out Stream de salida con el que se inicializará.
    */
    public RMIOutputStream(RMIOutputStreamInterf out) {
        this.out = out;
    }
    
    /**
    * Método que copia en el stream de salida el byte correspondiente
    * al entero que se especifica.
    * 
    * @param b Byte que se va a escribir en el stream de salida
    * @throws IOException En caso de error en la lectura/escritura.
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