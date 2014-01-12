import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AuthFileParser implements FileParser{

		public String filename;

		public AuthFileParser(String fn){
			this.filename = fn;
		}

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