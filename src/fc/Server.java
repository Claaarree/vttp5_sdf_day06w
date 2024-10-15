package fc;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
        //java -cp fortunecookie.jar fc.Server 12345 cookie_file.txt
        int port = Integer.parseInt(args[0]);
        File file = new File(args[1]);

        //Creating server
        ServerSocket server = new ServerSocket(port);
        System.out.println("Listening in on port " + port);

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        //Getting client connection
        while (true) {
            Socket clientConn = server.accept();
            System.out.println("Got a client connection!");

            CookieClientHandler CookieClientHandler = new CookieClientHandler(clientConn, file);

            //Submit work to thread pool
            threadPool.submit(CookieClientHandler);

        }   
        
    }
}