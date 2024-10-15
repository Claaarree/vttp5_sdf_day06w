package fc;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class CookieClientHandler implements Runnable {

    private Socket clientConn;
    private File file;

    public CookieClientHandler (Socket clientConn, File file) {
        this.clientConn = clientConn;
        this.file = file;
    }

    @Override
    public void run() {
         Cookie cookie = new Cookie();
            while (!clientConn.isClosed()) {
                String command;
                try {
                    command = cookie.getCommand(clientConn);
                                //System.out.println(command);
                if (command.equalsIgnoreCase("get cookie")) {
                    ArrayList<String> fortunes = cookie.readCookiefile(file);
                    //System.out.println(fortunes);
                    String theFortune = cookie.getRandomFortune(fortunes);
                    //System.out.println(theFortune);
                    cookie.sendCookie(clientConn, theFortune);
                } else if (command.equalsIgnoreCase("quit")) {
                    System.out.println("Closing client connection...");
                    clientConn.close();
                    System.exit(0);
                }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
    }

}