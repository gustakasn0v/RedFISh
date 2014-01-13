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

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

/**
 * Test.java
 *
 * Septiembre - Diciembre 2013
 *
 * DESCRIPCION DE LA CLASE.
 *
 * @author Andrea Balbás        09-10076
 * @author Gustavo El Khoury    10-10226
 */
public class Test{

    /**
    * Tamaño del buffer para copiar de un stream de entrada a uno
    * de salida.
    */
    private static Integer BUF_SIZE = 2048;

    /**
    * Copia los bytes de un stream de entrada a uno de salida..
    * 
    * @param in Stream de entrada.
    * @param out Stream de salida.
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
    * @param server Servidor de archivos al que se subirá el archivo.
    * @param src Nombre del archivo que se desea subir.
    * @param dest Nombre con que el archivo se subirá en el servidor de archivos.
    * @param owner Usuario que es propietario del archivo.
    * @return True en caso de que el archivo sea correctamente subido al servidor de archivos.
              False en caso de que el archivo no se haya subido al servidor de archivos.
    * @throws RemoteException En caso de error en la llamada remota.
    * @throws NotAuthenticatedException En caso de que el usuario no esté autenticado.
    */
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
    
    /**
    * Método para descargar un archivo del servidor de archivos.
    * 
    * @param server Servidor de archivos del que se descargará el archivo.
    * @param src Nombre del archivo que se desea descargar.
    * @param dest Nombre con que el archivo se descargará en el directorio local.
    * @param downloader Usuario que desea descargar el archivo.
    * @return True en caso de que el archivo sea correctamente subido al servidor de archivos.
              False en caso de que el archivo no se haya subido al servidor de archivos.
    * @throws RemoteException En caso de error en la llamada remota.
    * @throws NotAuthenticatedException En caso de que el usuario no esté autenticado.
    */
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

    /**
    * Programa principal del cliente.
    * El cliente se conecta al servidor de archivos. En caso de que se haya
    * colocado un archivo de comandos, se procesan los comandos allí escritos.
    * Si el archivo no contiene el comando 'sal', o si no se especificó un  
    * archivo de comandos, se dispone una consola para que el usuario
    * introduzca los comandos que desee ejecutar.
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

            String url = "rmi://"+host+":"+port+"/FileServer";
            FileServer server = (FileServer) Naming.lookup(url);

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
                                System.out.println("Comando no reconocido");
                }
            }
        }
        catch(NotBoundException nbe){
        System.out.println("El servidor no se encuentra iniciado");
        System.exit(0);
        }
        catch(MalformedURLException mue){
            System.out.println("Error no esperado con el URL del servidor");
            System.exit(0);
        }
        catch(RemoteException re){
            System.out.println("Excepción remota: "+re);
            re.printStackTrace();
            System.exit(0);
        }
        catch(NotAuthenticatedException nae){
            System.out.println("No estás autenticado");
            System.exit(0);
        }
        catch(NotAuthorizedException nae){
            System.out.println("No estás autorizado para realizar esa operación");
        }
    }
}