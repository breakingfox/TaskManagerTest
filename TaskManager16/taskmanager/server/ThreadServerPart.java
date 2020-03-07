/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager.server;


import taskmanager.server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import taskmanager.Message;
import taskmanager.TaskNode;
import taskmanager.client.Client;

import static taskmanager.server.Server.SOCKET_CLOSE;

/**
 * @author Rodion
 */
public class ThreadServerPart extends Thread {
    private final Socket clientSocket;
    private static final int CREATE_SUCCEFUL = 0;
    private static final int DELETE_SUCCEFUL = 0;
    private static final String CREATE_MESSAGE = "CREATE";
    private static final String DELETE_MESSAGE = "DELETE";
    private static final String EXIT_MESSAGE = "EXIT";
    private static final String SHOW_MESSAGE = "SHOW";
    private static final String NOTIFICATION_MESSAGE = "NOTIFICATION";
    private NotificationThread notificationThread;
    DataInputStream in;
    DataOutputStream out;

    public ThreadServerPart(Socket s) throws IOException {
        this.clientSocket = s;
        this.notificationThread= new NotificationThread();
    }

    @Override
    public void run() {

        try {


            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            System.out.println("Server accept client");
            notificationThread.start();
            int numOfTask;



            while (true) {

                ObjectInputStream objectIn = new ObjectInputStream(in);

                Message message = (Message) objectIn.readObject();
                if (message.getMessage().equals(DELETE_MESSAGE)) {

                    numOfTask = (Integer) message.getValue();
                    System.out.println(" client DELETE from Log - Task№ " + numOfTask);
                    if (Server.controller.deleteTask(numOfTask))
                        out.writeInt(DELETE_SUCCEFUL);
                    else
                        out.writeInt(-1);

                } else if (message.getMessage().equals(CREATE_MESSAGE)) {
                    System.out.println("client CREATE new Task in Log");

                    TaskNode newTask = (TaskNode) message.getValue();
                    if (Server.controller.createTask(newTask))
                        out.writeInt(CREATE_SUCCEFUL);
                    else
                        out.writeInt(-1);
                } else if (message.getMessage().equals(EXIT_MESSAGE)) {
                    out.writeInt(SOCKET_CLOSE);
                    System.out.println("client closed");
                    clientSocket.close();
                    in.close();
                    out.close();
                    notificationThread.clientSocketThread.close();
                    notificationThread.serverThread.close();

                    break;
                } else if (message.getMessage().equals(SHOW_MESSAGE)) {
                    ObjectOutputStream objectOutput = new ObjectOutputStream(out);
                    if (Server.controller.getLog() != null)
                        objectOutput.writeObject(Server.controller.getLog());

                    objectOutput.flush();

//                } else if (message.getMessage().equals(NOTIFICATION_MESSAGE)) {
//                    System.out.println("Сообщение от клиента");

                } else {
                    out.writeInt(SOCKET_CLOSE);
                    System.out.println("Error message||");
                    clientSocket.close();
                    in.close();
                    out.close();

                    break;

                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadServerPart.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ThreadServerPart.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ThreadServerPart.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            notificationThread.interrupt();
        }
    }


}
    

