import java.rmi.*;
import java.net.*;
import java.io.*;
import java.rmi.registry.*;
import java.util.LinkedList;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

/**
 * a_rmifs.java
 *
 * Septiembre - Diciembre 2013
 *
 * Implementación del servidor de autenticación.
 *
 * Grupo: 42.
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 * 
 */
public class a_rmifs {

     /**
    * Programa principal del servidor de autenticación.
    * El servidor analiza el archivo con los usuarios y claves,
    * y agrega a la base de datos los usuarios que se encuentran especificados
    * en el archivo. Si se especificó un puerto, será utilizado por el registry
    * para contener la información de los objetos remotos publicados por el 
    * servidor de autenticación; de lo contrario, se utiliza por defecto el
    * puerto 20226.
    */
  public static void main(String[] args) {
  	int port = 20226;

  	try {
	    Options cliOptions = new Options();
	    String filename = "";

	    cliOptions.addOption("help", false, "Print help for this application");
	    cliOptions.addOption("f", true, "The user database file");
	    cliOptions.addOption("p", true, "The port to use. The default port is 20226");

	    BasicParser cliParser = new BasicParser();
	    CommandLine cl = cliParser.parse(cliOptions, args);

	    if ( cl.hasOption("help") ) {
	        HelpFormatter helper = new HelpFormatter();
	        helper.printHelp("s_rmifs -f dbfile -p port", cliOptions);
		System.exit(0);
	    }
	    else {
	    	if ( !cl.hasOption('f') ) {
	    		System.out.println("A database file must be specified with -f option");
		        HelpFormatter helper = new HelpFormatter();
		        helper.printHelp("java a_rmifs -f authfile -p port", cliOptions);
		        System.exit(0);
	    	}
	    	else{
	    		filename = cl.getOptionValue("f");
	    		if ( cl.hasOption('p') ) {
		    		port = Integer.parseInt(cl.getOptionValue("p"));
	    		}
	    	}
	    }

	    AuthDatabaseImpl authDatabase = new AuthDatabaseImpl();
	    AuthFileParser fileParser = new AuthFileParser(filename);
	    LinkedList<User> userList = fileParser.parse();
	    for(User user : userList) {
	    	authDatabase.addUser(user);
	    }
	    

	    Registry registry = LocateRegistry.createRegistry( port );
	    registry.rebind("Auth", authDatabase);
	}

	catch (ParseException e) {
	    e.printStackTrace();
	}
	
	catch(FileNotFoundException e){
		System.out.println("Error reading file");
		e.printStackTrace();
	}

    catch(RemoteException re) {
    	System.out.println("RemoteException: " + re);
    }
    while(true);

  }
}
