import java.io.Serializable;

public class User implements Serializable, Comparable{
	public String username;
	public String password;

	public User(String user, String pass){
		this.username = user;
		this.password = pass;
	}

	public Boolean equals(Object u){
		System.out.println(u instanceof User);
		if (!(u instanceof User)) return false;
		else {
			User u2 = (User) u;
			System.out.println("LOS datos son");
	        System.out.println("|"+this.username+"|");
	        System.out.println("|"+this.password+"|");
	        System.out.println("Por parametro llega");
	        System.out.println("|"+u2.username+"|");
	        System.out.println("|"+u2.password+"|");
			return (u.username.equals(this.username) && u.password.equals(this.password));
		}
	}
}