import java.rmi.*; // For Naming, RemoteException, etc.
import java.net.*; // For MalformedURLException
import java.io.*;  // For Serializable interface
import java.rmi.registry.*;
import java.util.LinkedList;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

/** Get a Rem object from the specified remote host.
 *  Use its methods as though it were a local object.
 * @see Rem
 */

public class a_rmifs {
  public static void main(String[] args) {
  	int port = 20226;

  	try {
	    Options cliOptions = new Options();
	    String filename = "";

	    cliOptions.addOption("h", false, "Print help for this application");
	    cliOptions.addOption("f", true, "The user database file");
	    cliOptions.addOption("p", true, "The port to use. The default port is 20226");

	    BasicParser cliParser = new BasicParser();
	    CommandLine cl = cliParser.parse(cliOptions, args);

	    if ( cl.hasOption('h') ) {
	        HelpFormatter helper = new HelpFormatter();
	        helper.printHelp("RMIFS Authentication Server", cliOptions);
	    }
	    else {
	    	if ( !cl.hasOption('f') ) {
	    		System.out.println("A database file must be specified with -f option");
		        HelpFormatter helper = new HelpFormatter();
		        helper.printHelp("RMIFS Authentication Server", cliOptions);
	    	}
	    	else{
	    		filename = cl.getOptionValue("f");
	    		if ( cl.hasOption('p') ) {
		    		port = Integer.parseInt(cl.getOptionValue("p"));
	    		}
	    	}
	    }

	    AuthDatabaseImpl authDatabase = new AuthDatabaseImpl();
	    LinkedList<User> userList = FileParser.AuthFileParser.parse(filename);
	    for(User user : userList) authDatabase.addUser(user);
	    

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

//     try {
//       String host =
//         (args.length > 0) ? args[0] : "localhost";
        
//       String port =
//         (args.length > 0) ? args[1] : "20226";
        
//       Registry registry=LocateRegistry.getRegistry(
//                host,
//                (new Integer(port)).intValue()
//       );
//       // Get remote object and store it in remObject:
//       Rem remObject =
//         (Rem)registry.lookup("Rem");
//       // Call methods in remObject:
//       System.out.println(remObject.getMessage());
//     }
//     catch(RemoteException re) {
//       System.out.println("RemoteException: " + re);
//     }
//     catch(NotBoundException nbe) {
//       System.out.println("NotBoundException: " + nbe);
//     }
// //    catch(MalformedURLException mfe) {
// //      System.out.println("MalformedURLException: "
// //                         + mfe);
//   //  }
  }
}
