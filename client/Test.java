import java.rmi.*;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.LinkedList;

public class Test{
	private static Integer BUF_SIZE = 2048;

	private static void copy(InputStream in, OutputStream out) 
            throws IOException {
        System.out.println("using byte[] read/write");
        byte[] b = new byte[BUF_SIZE];
        int len;
        while ((len = in.read(b)) >= 0) {
            out.write(b, 0, len);
        }
        in.close();
        out.close();
    }

    public static Boolean uploadFile(FileServer server,String src, String dest,User owner) throws RemoteException,NotAuthenticatedException{
		try{
			copy (new FileInputStream(src), server.getOutputStream(new File(dest),owner));
		}
		
		catch(FileNotFoundException e){
			System.out.println("El archivo especificado no existe:");
			return false;
		}

		catch(IOException e){
			System.out.println("Excepción no esperada de IO");
			e.printStackTrace();
		}
		return true;
		
	}

	public static Boolean downloadFile(FileServer server,String src, String dest,User downloader) throws RemoteException,NotAuthenticatedException{
		try{
			copy (server.getInputStream(new File(src),downloader), new FileOutputStream(dest));
		}
		
		catch(FileNotFoundException e){
			System.out.println("El archivo especificado no existe:");
			return false;
		}

		catch(IOException e){
			System.out.println("Excepción no esperada de IO");
			e.printStackTrace();
		}
		return true;
		
	}


	public static void main(String[] args) {
	  	int port = 30226;
	  	String host = "localhost";
	  	String authfile = "";
	  	String commandfile = "";

	  	try {
		    Options cliOptions = new Options();
		    String filename = "";

		    cliOptions.addOption("help", false, "Print help for this application");
		    cliOptions.addOption("m", false, "The host of the File server. Defaults to localhost");
		    cliOptions.addOption("p", true, "The port to use. Defaults to 30226");
		    cliOptions.addOption("f", true, "The authentication file");
		    cliOptions.addOption("c", true, "The commands file");

		    BasicParser cliParser = new BasicParser();
		    CommandLine cl = cliParser.parse(cliOptions, args);

		    if ( cl.hasOption("help") || !cl.hasOption("p") 
		    	|| !cl.hasOption("m") ) {
		        HelpFormatter helper = new HelpFormatter();
		        helper.printHelp("java c_rmifs -m server -p port [-f authfile] [-c commandfile]", cliOptions);
		    }
		    else {
		    	if ( cl.hasOption('m') ) {
		    		host = cl.getOptionValue("m");
		    	}
		    	if ( cl.hasOption('p') ) {
		    		port = Integer.parseInt(cl.getOptionValue("p"));
		    	}
		    	if ( cl.hasOption('f') ) {
		    		authfile = cl.getOptionValue("f");
		    	}
		    	if ( cl.hasOption('m') ) {
		    		commandfile = cl.getOptionValue("c");
		    	}
		    }

		    FileServerImpl fileServer = null;
		    try{
		    	fileServer = new FileServerImpl(host,port);
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
		    
		    

		    Registry registry = LocateRegistry.createRegistry( port );
		    System.out.print("Iniciando el servidor:\t\t\t");
		    registry.rebind("FileServer", fileServer);
		    System.out.println("[   OK   ]");

		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    String command, fullString;
		    while(true){
		    	fullString = bufferRead.readLine();
		    	command = fullString.substring(0,2);
		    	switch(command){
		    		case "rls":
		    			//
		    			break;
		    		case "lls":
		    			//
		    			break;
		    		case "sub":
		    		//
		    			break;
		    		case "baj":
		    		//
		    			break;
		    		case "bor":
		    		//
		    			break;
		    		case "inf":
		    		//
		    			break;
		    		case "sal":
		    		//
		    			break;
		    		default:
		    			System.out.println("Comando no reconocido")
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