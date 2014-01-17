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

/**
 * sc_rmifs.java
 *
 * Septiembre - Diciembre 2013
 *
 * Programa del servidor de archivos.
 *
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class s_rmifs {

        /**
        * Programa principal del servidor de archivos.
        * El servidor analiza las opciones con las que haya sido ejecutado,
        * y en base a eso inicializa los valores correspondientes al servidor
        * de autenticación, el puerto de autenticación y el puerto a utilizar.
        * En caso de que no se especifique un servidor, por defecto
        * se utiliza localhost, si no se especifica un puerto, se utiliza
        * el puerto 30226, y si no se especifica un puerto de autenticación,
        * por defecto se utiliza el puerto 20226.
        */
	public static void main(String[] args) {
	  	int port = 30226;
	  	int authPort = 20226;
	  	String authHost = "localhost";

	  	try {
		    Options cliOptions = new Options();
		    String filename = "";

		    cliOptions.addOption("help", false, "Print help for this application");
		    cliOptions.addOption("h", true, "The host of the Authentication server. Defaults to localhost");
		    cliOptions.addOption("r", true, "The port of the Authentication server. Defaults to 20226");
		    cliOptions.addOption("l", true, "The port to use. Defaults to 30226");

		    BasicParser cliParser = new BasicParser();
		    CommandLine cl = cliParser.parse(cliOptions, args);

		    if ( cl.hasOption("help") ) {
		        HelpFormatter helper = new HelpFormatter();
		        helper.printHelp("java s_rmifs -l port -h authhost -r authport", cliOptions);
		        System.exit(0);
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

		    FileServerImpl fileServer = null;
		    try{
		    	fileServer = new FileServerImpl(authHost,authPort);
		    }
		    catch (RemoteException re){
		    	System.out.println(re);
		    	re.printStackTrace();
		    	System.exit(0);
		    }
		    catch (NotBoundException nbe){
		    	System.out.println(nbe);
		    	nbe.printStackTrace();
		    	System.exit(0);
		    }
		    
		    
                    /*
                    * Se inicializa el servidor.
                    */
		    Registry registry = LocateRegistry.createRegistry( port );
		    System.out.print("Iniciando el servidor:\t\t\t");
		    registry.rebind("FileServer", fileServer);
		    System.out.println("[   OK   ]");

		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    String command, fullString;
		    /*
		    * Se dispone una consola para ejecución de comandos
		    */
		    while(true){
			System.out.print("$>");
		    	fullString = bufferRead.readLine();
		    	switch(fullString){
		    		case "log":
		    			System.out.println(fileServer.getHistory());
		    			break;
		    		case "sal":
			    		registry.unbind("FileServer");
			            fileServer.unexportObject(fileServer, true);
			            System.out.print("Deteniendo el servidor:\t\t\t");
			            System.out.println("[   OK   ]");
			            System.exit(0);
		    			break;
		    		default:
		    			System.out.println("Comando no reconocido");
		    	}
		    }
		}
		catch (IOException e) {
			if (e instanceof RemoteException){
				System.out.println("[ FAILED ]");
				System.out.println("Error contactando registry");
			}
			else if (e instanceof MalformedURLException){
				System.out.println("[ FAILED ]");
				System.out.println("URL de servidor malformado");
			}
			else{
				System.out.println("Error leyendo la entrada estandar");
			}
			e.printStackTrace();
			System.exit(0);
		}

		catch (NotBoundException e) {
			System.out.println("Error deteniendo el servidor");
			System.out.println(e);
		    e.printStackTrace();
		    System.exit(0);
		}

		catch (ParseException e) {
			System.out.println("Error leyendo opciones de consola");
			System.out.println(e);
		    e.printStackTrace();
		    System.exit(0);
		}
	  }
}
