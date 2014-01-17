import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * FileParser.java
 *
 * Septiembre - Diciembre 2013
 *
 * Interfaz para hacer el análisis del archivo que contiene los usuarios
 * autorizados para acceder al servidor de archivos y sus contraseñas
 * @author Andrea Balbás        09-10076
 *
 * Grupo: 42.
 * @author Gustavo El Khoury    10-10226
 */
public interface FileParser{

        /**
        * Método que realiza el análisis del archivo.
        * @return Lista con los usuarios existentes en el archivo 
        * y sus contraseñas.
        * @throws FileNotFoundException Si el archivo que se intenta analizar
        *         no existe.
        */
	public LinkedList parse() throws FileNotFoundException;
}