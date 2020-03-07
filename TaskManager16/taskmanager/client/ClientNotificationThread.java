package taskmanager.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import taskmanager.Message;
import taskmanager.TaskLog;
import taskmanager.TaskNode;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ClientNotificationThread extends Thread {
    public Socket socket;

    ClientNotificationThread() throws IOException {
        socket = new Socket("localhost", 4004);
        System.out.println("ClientThread connected to socket.");
    }

    @Override
    public void run() {
        try {


            DataInputStream in = new DataInputStream(socket.getInputStream());
            System.out.println("threads connected");
            while (true) {

                ObjectInputStream objectInput = new ObjectInputStream(in);
                System.out.println("Client wait notification");
                Message message = (Message) objectInput.readObject();

                if (message.getMessage().equals("NOTIFICATION")) {
                    TaskNode node = (TaskNode) message.getValue();
                    File soundFile = new File("snd.wav");
                    if(soundFile.exists())
                    {
                        AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
                        Clip clip = AudioSystem.getClip();
                        clip.open(ais);
                        clip.setFramePosition(0);

                        clip.start();
                    }
                    JOptionPane.showMessageDialog(Client.getFrame(), "Уведомление!\n" + node.getTaskName() +"\n"+ node.getTaskDescription());
                }

                ClientNotificationThread.sleep(4000);


            }
        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            e.printStackTrace();


        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(ClientNotificationThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(ClientNotificationThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}