/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import taskmanager.Message;
import taskmanager.TaskLog;
import taskmanager.server.Server;

/**
 * @author Rodion
 */
public class Client {
    public static final int CREATE_SUCCEFUL = 0;
    public static final int DELETE_SUCCEFUL = 0;
    private static Socket clientSocket;
    public static DataInputStream in;
    public static DataOutputStream out;

    public static JFrame getFrame() {
        return frame;
    }

    private static JFrame frame;
    private static final String NOTIFICATION_MESSAGE = "NOTIFICATION";

    public static void createWindow() throws IOException, ClassNotFoundException {
        frame = new JFrame();
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        JPanel panel = new JPanel();
        ClientNotificationThread clientThread = new ClientNotificationThread();
        clientThread.start();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton createButton = new JButton("CREATE");
        JButton deleteButton = new JButton("DELETE");
        JButton showButton = new JButton("SHOW TASKS");
        JButton exitButton = new JButton("EXIT");
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    ObjectOutputStream objectOutput = new ObjectOutputStream(out);
                    objectOutput.writeObject(new Message(1, "SHOW"));
                    objectOutput.flush();
                    ObjectInputStream objectInput = new ObjectInputStream(in);

                    TaskLog log = (TaskLog) objectInput.readObject();
                    if (!log.isEmpty()) {
                        ShowWindow.index = 0;
                        new ShowWindow(log);
                    } else
                        JOptionPane.showMessageDialog(Client.frame, "Список пуст!");
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }


            }
        });
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateWindow();

            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteWindow();

            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ObjectOutputStream objectOutput = new ObjectOutputStream(Client.out);

                    objectOutput.writeObject(new Message(1, "EXIT"));
                    objectOutput.flush();

                    int isClosed = in.readInt();

                    if (isClosed == Server.SOCKET_CLOSE) {
                        System.out.println("Клиент был закрыт...");
                        clientSocket.close();
                        in.close();
                        out.close();
                        clientThread.socket.close();
                        clientThread.interrupt();
                        System.exit(0);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panel.add(createButton);
        panel.add(deleteButton);
        panel.add(showButton);
        panel.add(exitButton);
        frame.getContentPane().add(panel);
        frame.setSize(300, 150);
        frame.setVisible(true);

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        clientSocket = new Socket("localhost", 4004);
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());
        System.out.println("Client connected to socket.");
        System.out.println(out);

        createWindow();
    }
}