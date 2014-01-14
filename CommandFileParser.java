import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * CommandFileParser.java
 *
 * Septiembre - Diciembre 2013
 *
 * Implementación de la interfaz FileParser, que se utilizará para hacer
 * el análisis del archivo que contiene los comandos que el cliente ejecuta
 * Implementa la interfaz FileParser.
 *
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class CommandFileParser implements FileParser{

                /**
                * Nombre del archivo que se va a analizar.
                */
		public String filename;
		public User executor;

		/**
                * Constructor de la clase.
                * @param fn Nombre del archivo a analizar
                */
		public CommandFileParser(String fn,User executor){
			this.filename = fn;
			this.executor = executor;
		}

		/**
                * Método que realiza el análisis del archivo.
                * @return Lista con los usuarios existentes en el archivo 
                * y sus contraseñas.
                * @throws FileNotFoundException Si el archivo que se intenta analizar
                *         no existe.
                */
		public LinkedList<FileServerCommand> parse() throws FileNotFoundException{
			LinkedList<FileServerCommand> commandList = new LinkedList<FileServerCommand>();
			BufferedReader fileHandler = new BufferedReader(new FileReader(this.filename));
			String line;
			String command,arg;
			try{
				while ((line = fileHandler.readLine()) != null){
					line = line.trim();
					command = line.substring(0,3).trim();
		    		arg = line.substring(3,line.length()).trim();
					commandList.add(new FileServerCommand(command,arg,this.executor));
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
			
			return commandList;
		}

	}