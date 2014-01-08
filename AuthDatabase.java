import java.rmi.*;

/** The RMI client will use this interface directly.
 *  The RMI server will make a real remote object that
 *  implements this, then register an instance of it
 *  with some URL.
 */

public interface AuthDatabase extends Remote {
	public Boolean addUser(String username, String password);

	public Boolean authenticate(String username, String password);
}
