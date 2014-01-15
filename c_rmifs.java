import java.rmi.*;
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
import java.io.Console;
import java.io.File;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

/**
 * c_rmifs.java
 *
 * Septiembre - Diciembre 2013
 *
 * Programa cliente. 
 *
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class c_rmifs{

        /**
        *Tamaño del buffer.
        */
	private static Integer BUF_SIZE = 2048;

        /**
        * Lista los nombres de los archivos presentes en el directorio
        * actual de trabajo del cliente.
        *
        * @return String con los nombres de archivos del directorio
        *         de trabajo actual del cliente.
        */
	private static String listFilesInCWD(){
		String path = "."; 

		String files = "";
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 

		for (int i = 0; i < listOfFiles.length; i++){
			if (listOfFiles[i].isFile()){
				files += listOfFiles[i].getName()+"\n";
			}
		}
		return files;
	}

	/**
        * Método para copiar los bytes de un stream de bytes de entrada a
        * un stream de bytes de salida.
        *
        * @param in Stream de bytes de entrada.
        * @param out Stream de de bytes salida.
        * @throws IOException En caso de error en la lectura/escritura.
        */
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

        /**
        * Método para subir un archivo al servidor de archivos.
        *
        * @param server Servidor de archivos al que se desea subir el archivo.
        * @param src Nombre del archivo que se desea subir al servidor.
        * @param owner Usuario que sube el archivo y que por tanto será 
        *        su propietario.
        * @throws RemoteException En caso de error en la llamada remota. 
        * @throws NotAuthenticatedException En caso de que el usuario no esté
        *          autenticado.        
        */
        public static Boolean uploadFile(FileServer server,String src, User owner) throws RemoteException,NotAuthenticatedException{
                try{
                        copy (new FileInputStream(src), server.getOutputStream(new File(src),owner));
                }
                
                catch(FileNotFoundException e){
                        System.out.println("El archivo especificado no existe:");
                        return false;
                }

                catch(FileExistsException fee){
                    System.out.println("El archivo ya existe en el servidor");
                }

                catch(IOException e){
                        System.out.println("Excepción no esperada de IO");
                        e.printStackTrace();
                }
                return true;
                
        }

	/**
        * Método para descargar un archivo del servidor de archivos.
        *
        * @param server Servidor de archivos del que se desea descargar 
        *        el archivo.
        * @param src Nombre del archivo que se desea descargar del servidor.
        * @param downloader Usuario que descarga el archivo.
        * @throws RemoteException En caso de error en la llamada remota. 
        * @throws NotAuthenticatedException En caso de que el usuario no esté
        *          autenticado.
        */
	public static Boolean downloadFile(FileServer server,String src,User downloader) throws RemoteException,NotAuthenticatedException{
		try{
			copy (server.getInputStream(new File(src),downloader), new FileOutputStream(src));
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

	/**
        * Método para ejecutar los comandos disponibles para el cliente.
        *
        * @param command Comando que se desea ejecutar.
        * @param arg Argumento del comando que se desea ejecutar.
        * @param server Servidor de archivos que se utiliza en la ejecución
        *        de los comandos del cliente.
        * @param myOwner Usuario que ejecuta el comando.
        * @throws RemoteException En caso de error en la llamada remota. 
        * @throws NotAuthenticatedException En caso de que el usuario no esté
        *          autenticado.
        */
	private static void executeCommands(String command, String arg, FileServer server, User myOwner)
	throws RemoteException,NotAuthenticatedException {
             switch(command){
                    case "rls":
                            System.out.println(server.listFiles(myOwner));
                            break;
                    case "lls":
                            System.out.println(listFilesInCWD());
                            break;
                    case "sub":
                            uploadFile(server,arg,myOwner);
                            break;
                    case "baj":
                            downloadFile(server,arg,myOwner);
                            break;
                    case "bor":
                            try{
                                    server.deleteFile(arg,myOwner);
                            }
                            catch(NotAuthorizedException nae){
                                    System.out.println("No estás autorizado para realizar esa operación");
                                    }
                                    catch(FileNotFoundException fnfe){
                                    System.out.println("El archivo especificado no existe");
                                    }
                            break;
                    case "inf":
                    //
                            break;
                    case "sal":
                            System.exit(0);
                            break;
                    default:
                            System.out.println("Comando no reconocido");
                            System.out.print("$>");
            }
	}

	/**
        * Programa principal del cliente.
        * El cliente analiza las opciones con las que haya sido ejecutado,
        * y en base a eso inicializa los valores correspondientes al servidor
        * de archivos, el puerto a utilizar, el archivo con las combinaciones
        * de usuario/contraseña para la autenticación, y el archivo de
        * comandos. En caso de que no se especifique un servidor, por defecto
        * se utiliza localhost, y si no se especifica un puerto, se utiliza
        * el puerto 30226.
        */
	public static void main(String[] args) {
	  	int port = 30226;
	  	String host = "localhost";
	  	String authfile = "";
	  	String commandfile = "";

	  	try {
		    Options cliOptions = new Options();
		    String filename = "";

		    cliOptions.addOption("help", false, "Print help for this application");
		    cliOptions.addOption("m", true, "The host of the File server. Defaults to localhost");
		    cliOptions.addOption("p", true, "The port to use. Defaults to 30226");
		    cliOptions.addOption("f", true, "The authentication file");
		    cliOptions.addOption("c", true, "The commands file");

		    BasicParser cliParser = new BasicParser();
		    CommandLine cl = cliParser.parse(cliOptions, args);

		    if ( cl.hasOption("help") ) {
		        HelpFormatter helper = new HelpFormatter();
		        helper.printHelp("java c_rmifs -m server -p port [-f authfile] [-c commandfile]", cliOptions);
		        System.exit(0);
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
		    	if ( cl.hasOption('c') ) {
		    		commandfile = cl.getOptionValue("c");
		    	}
		    }

		    String url = "rmi://"+host+":"+port+"/FileServer";
            FileServer server = (FileServer) Naming.lookup(url);

            Boolean authenticated = false;
            User myOwner = null;
            
            /*
            * Si se especificó un archivo de combinaciones de usuario/clave
            * para la autenticación, se van agregando uno a uno a la lista de
            * usuarios autenticados.
            */
            if (!authfile.equals("")){
	            AuthFileParser fileParser = new AuthFileParser(filename);
		    	LinkedList<User> userList = fileParser.parse();
		    	Boolean currentIsCorrect = false;
		    	for(User user : userList) {
		    		currentIsCorrect = server.testUser(user);
		    		if (currentIsCorrect) myOwner = user;
		    		else {
		    			System.out.println("Autenticación por archivo incorrecta para el "
		    				+user);
		    			authenticated = authenticated  || currentIsCorrect;
		    		}
		    	}
		    }

		    Console console = System.console();

		    /*
		    * Si no se auteticó ningún usuario mediante el archivo,
		    * se solicitan las credenciales por consola.
		    */
		    if (!authenticated){
		    	User testUser = null;
		    	while(!authenticated){
			    	String username,password;
			    	System.out.print("Introduzca su nombre de usuario: ");
			    	username = console.readLine();
			    	System.out.print("Introduzca su contraseña: ");
			    	char[] passwordChars = console.readPassword();
	        		password = new String(passwordChars);
			    	testUser = new User(username,password);
			    	authenticated = server.testUser(testUser);
			    	if (!authenticated) 
			    		System.out.println("Usuario o contraseña inválidos. Intente de nuevo");
		    	}
		    	myOwner = testUser;
		    }

		    /*
		    * Si se especificó un archivo de comandos, se ejecutan.
		    */
		    Boolean runShell = true;
		    if (!commandfile.equals("")){
		    	CommandFileParser commParser = new CommandFileParser(commandfile,myOwner);
		    	LinkedList<FileServerCommand> commList = commParser.parse();
		    	for(FileServerCommand comm : commList){
		    		executeCommands(comm.command,comm.argument,server,comm.executor);
		    		runShell = !comm.argument.equals("sal");
		    	}

		    }
		    
		    /*
		    * Si ninguno de los comandos del archivo es 'sal',
		    * se solicitan comandos por consola.
		    */
		    if (runShell){
		    	String command, fullString, arg;
			    while(true){
			    	System.out.print("$>");
			    	fullString = console.readLine();
			    	command = fullString.substring(0,3).trim();
			    	arg = fullString.substring(3,fullString.length()).trim();
			    	executeCommands(command,arg,server,myOwner);
			    }	
		    }
		    
		}

		catch(NotBoundException nbe){
            System.out.println("El servidor no se encuentra iniciado");
            System.exit(0);
        }

        
        catch(NotAuthenticatedException nae){
            System.out.println("No estás autenticado");
            System.exit(0);
        }

        catch (IOException e) {
			if (e instanceof RemoteException){
				System.out.println("Error de llamadas a servidor");
			}
			else if (e instanceof MalformedURLException){
				System.out.println("URL de servidor malformado");
			}
			else{
				System.out.println("Error leyendo la entrada estandar");
			}
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