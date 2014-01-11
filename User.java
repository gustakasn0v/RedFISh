import java.io.Serializable;

public class User implements Serializable{
	public String username;
	public String password;

	public User(String user, String pass){
		this.username = user;
		this.password = pass;
	}

	public boolean equals(Object u){
		System.out.println(u instanceof User);
		if (!(u instanceof User)) return false;
		else {
			User u2 = (User) u;
			return (u2.username.equals(this.username) && u2.password.equals(this.password));
		}
	}
}