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
		try{
			String url = "rmi://localhost:30226/FileServer";
	        FileServer server = (FileServer) Naming.lookup(url);
	        
	        File testFile = new File("prueba.txt");
	        long len = testFile.length();
	        
	        long t;
	        t = System.currentTimeMillis();
	        
	        uploadFile(server,"prueba.txt", "download.tif",new User("dreabalbas","123"));
	        //server.deleteFile("prueba.txt",new User("dreabalbas","1234"));

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
		catch(NotAuthenticatedException nae){
			System.out.println("Not authenticated");
			System.exit(0);
		}
		// catch(NotAuthorizedException nae){
		// 	System.out.println("Not authorized");
		// 	System.exit(0);
		// }
		
	}
}