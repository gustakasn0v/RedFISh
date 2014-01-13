import java.io.Serializable;

/**
 * User.java
 *
 * Septiembre - Diciembre 2013
 *
 * Implementación la clase Usuario.
 * Implementa la interfaz Serializable.
 *
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class User implements Serializable{

        /**
        * Nombre de usuario
        */
	public String username;
	
	/**
        * Contraseña
        */
	public String password;

	 /**
        * Constructor de la clase.
        * 
        * @param user Nommbre de usuario del usuario a crear.
        * @param pass Contraseña del usuario a crear.
        */
	public User(String user, String pass){
		this.username = user;
		this.password = pass;
	}

	/**
        * Método para comparar un usuario con algún objeto que sea
        * especificado como argumento.
        * 
        * @param u Objeto con el que se va a comparar el usuario en cuestión.
        */
	public boolean equals(Object u){
		if (!(u instanceof User)) return false;
		else {
			User u2 = (User) u;
			return (u2.username.equals(this.username) && u2.password.equals(this.password));
		}
	}

	/**
        * Método para expresar en forma de String la información relacionada
        * a un usuario.
        */
	public String toString(){
		return "Usuario: "+this.username;
	}
}