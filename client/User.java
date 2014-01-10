import java.io.Serializable;

public class User implements Serializable{
	public String username;
	public String password;

	public User(String user, String pass){
		this.username = user;
		this.password = pass;
	}
}
