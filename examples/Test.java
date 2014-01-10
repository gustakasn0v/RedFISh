import java.rmi.*;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

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

    public static Boolean uploadFile(FileServer server,String src, String dest) throws RemoteException{
		try{
			//copy (new FileInputStream(src), server.getOutputStream(new RMIFile(dest)));
			copy (new FileInputStream(src), server.owner);
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

	public static Boolean downloadFile(FileServer server,String src, String dest) throws RemoteException{
		try{
			copy (server.getInputStream(new File(src)), new FileOutputStream(dest));
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


public static void download(ServerInterf server, File src, 
            File dest) throws IOException {
        
    }


	public static void main(String[] args) {
		try{
			String url = "rmi://localhost:30226/FileServer";
	        FileServer server = (FileServer) Naming.lookup(url);
	        
	        File testFile = new File("prueba.txt");
	        long len = testFile.length();
	        
	        long t;
	        t = System.currentTimeMillis();
	        
	        uploadFile(server,"prueba.txt", "download.tif");
	        //t = (System.currentTimeMillis() - t) / 1000;
	        //System.out.println("download: " + (len / t / 1000000d) + " MB/s");
		}
		catch(NotBoundException nbe){
			System.out.println("Not bound");
			System.exit(0);
		}
		catch(MalformedURLException mue){
			System.out.println("Malformed");
			System.exit(0);
		}
		
		catch(RemoteException re){
			System.out.println("RemoteException");
			re.printStackTrace();
			System.exit(0);
		}
		
	}
}