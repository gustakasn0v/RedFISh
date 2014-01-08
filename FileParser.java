import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileParser{

	public static class AuthFileParser{
		public static LinkedList<User> parse(String filename) throws FileNotFoundException{
			LinkedList<User> userList = new LinkedList<User>();
			BufferedReader fileHandler = new BufferedReader(new FileReader(filename));
			String line;
			String user,pass;
			try{
				while ((line = fileHandler.readLine().trim()) != null){
					user = line.substring(0,line.indexOf(':')).trim();
					pass = line.substring(line.indexOf(':'),line.length()).trim();
					userList.add(new User(user,pass));
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
			
			return userList;
		}

	}

}