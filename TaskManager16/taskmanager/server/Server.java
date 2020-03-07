/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager.server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import taskmanager.TaskController;


/**
 * @author Rodion
 */
public class Server {
    public static Socket clientSocket;
    public static TaskController controller;//сокет для общения
    private static ServerSocket server; // серверсокет
    public static int SOCKET_CLOSE = -1;
    public static int EXIT = -10;
    public static int NOT_NOTIFICATION = 0;
    public static int IT_NOTIFICATION = 1;

    public static void main(String[] args) throws ClassNotFoundException, SAXException, ParseException, ParserConfigurationException {

        try {
            controller = new TaskController();
            server = new ServerSocket(4004); // серверсокет прослушивает порт 4004
            System.out.println("Server started!");

            while (!server.isClosed()) {
                clientSocket = server.accept();
                ThreadServerPart serverPart = new ThreadServerPart(clientSocket);
                serverPart.start();
            }

        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
