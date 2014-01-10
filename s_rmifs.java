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

public class s_rmifs {
  public static void main(String[] args) {
  	int port = 30226;
  	int authPort = 20226;
  	String authHost = "localhost";

  	try {
	    Options cliOptions = new Options();
	    String filename = "";

	    cliOptions.addOption("help", false, "Print help for this application");
	    cliOptions.addOption("h", false, "The host of the Authentication server. Defaults to localhost");
	    cliOptions.addOption("r", true, "The port of the Authentication server. Defaults to 20226");
	    cliOptions.addOption("l", true, "The port to use. Defaults to 30226");

	    BasicParser cliParser = new BasicParser();
	    CommandLine cl = cliParser.parse(cliOptions, args);

	    if ( cl.hasOption("help") ) {
	        HelpFormatter helper = new HelpFormatter();
	        helper.printHelp("s_rmifs -l port -h authhost -r authport", cliOptions);
	    }
	    else {
	    	if ( cl.hasOption('h') ) {
	    		authHost = cl.getOptionValue("h");
	    	}
	    	if ( cl.hasOption('r') ) {
	    		authPort = Integer.parseInt(cl.getOptionValue("r"));
	    	}
	    	if ( cl.hasOption('l') ) {
	    		port = Integer.parseInt(cl.getOptionValue("l"));
	    	}
	    }

	    FileServerImpl fileServer = new FileServerImpl(authHost,authPort);
	    

	    Registry registry = LocateRegistry.createRegistry( port );
	    registry.rebind("FileServer", fileServer);
	}

	catch (ParseException e) {
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
