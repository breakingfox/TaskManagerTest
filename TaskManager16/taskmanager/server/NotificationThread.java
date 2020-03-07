package taskmanager.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;

public class NotificationThread extends Thread {
    public  ServerSocket serverThread;
    public Socket clientSocketThread;
    public static  DataOutputStream  out ;
    public NotificationThread() throws IOException{
        serverThread = new ServerSocket(3345);
        System.out.println("thread server started");
        clientSocketThread=serverThread.accept();
    }

    public void run() {
        try {

            out = new DataOutputStream(clientSocketThread.getOutputStream());
            while (true) {
                Server.controller.notification();


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
