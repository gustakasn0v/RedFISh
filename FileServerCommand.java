/**
 * FileServerCommand.java
 *
 * Septiembre - Diciembre 2013
 *
 * Implementación de la clase de los comandos del servidor.
 *
 * Grupo: 42.
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class FileServerCommand{
        /**
        * Nombre del comando.
        */
	public String command;
	
	/**
        * Argumento del comando.
        */
	public String argument;
	
	/**
        * Usuario que ejecutó el comando.
        */
	public User executor;

	/**
        * Constructor de la clase.
        * 
        * @param command Nombre del comando a crear.
        * @param argument Argumento del comando a crear.
        * @param executor Usuario que ejecuta el comando.
        */
	public FileServerCommand(String command, String argument, User executor){
		this.command = command;
		this.argument = argument;
		this.executor = executor;
	}
	
        /**
        * Método para expresar en forma de String la información relacionada
        * a un comando.
        *
        * @return String con la información del comando.
        */
	public String toString(){
		return "\nComando: " + this.command +
		" | Argumento: "+this.argument+" | " + this.executor.toString();
	}
}
