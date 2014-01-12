import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileParser{
	public LinkedList<User> parse() throws FileNotFoundException;
}