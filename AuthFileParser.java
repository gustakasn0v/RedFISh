import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * AuthFileParser.java
 *
 * Septiembre - Diciembre 2013
 *
 * Implementación de la interfaz FileParser, que se utilizará para hacer
 * el análisis del archivo que contiene los usuarios autorizados para acceder
 * al servidor de archivos.
 * Implementa la interfaz FileParser.
 *
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class AuthFileParser implements FileParser{

                /**
                * Nombre del archivo que se va a analizar.
                */
		public String filename;

		/**
                * Constructor de la clase.
                * @param fn Nombre del archivo a analizar
                */
		public AuthFileParser(String fn){
			this.filename = fn;
		}

		/**
                * Método que realiza el análisis del archivo.
                * @return Lista con los usuarios existentes en el archivo 
                * y sus contraseñas.
                * @throws FileNotFoundException Si el archivo que se intenta analizar
                *         no existe.
                */
		public LinkedList<User> parse() throws FileNotFoundException{
			LinkedList<User> userList = new LinkedList<User>();
			BufferedReader fileHandler = new BufferedReader(new FileReader(this.filename));
			String line;
			String user,pass;
			try{
				while ((line = fileHandler.readLine()) != null){
					line = line.trim();
					user = line.substring(0,line.indexOf(':')).trim();
					pass = line.substring(line.indexOf(':')+1,line.length()).trim();
					userList.add(new User(user,pass));
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
			
			return userList;
		}

	}