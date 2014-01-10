import java.io.Serializable;

public class User implements Serializable, Comparable{
	public String username;
	public String password;

	public User(String user, String pass){
		this.username = user;
		this.password = pass;
	}

	public Boolean equals(Object u){
		if (!(u instanceof User)) return false;
		else return (u.username == this.username) && (u.password == this.password);
	}
}