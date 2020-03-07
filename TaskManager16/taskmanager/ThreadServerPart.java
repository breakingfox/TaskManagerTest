/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static taskmanager.Server.SOCKET_CLOSE;

/**
 *
 * @author Rodion
 */
public class ThreadServerPart extends Thread {
private final Socket clientSocket;
private static final int CREATE=0;
private static final int DELETE=1;
private static final int CREATE_SUCCEFUL=0;
private static final int DELETE_SUCCEFUL=0;
public ThreadServerPart(Socket s)
     {
         this.clientSocket=s;
         
        
     }
    @Override
    public void run() 
    {
           try {
                    DataInputStream in; 
                    DataOutputStream out;
                    in = new  DataInputStream(clientSocket.getInputStream());
                    out = new DataOutputStream(clientSocket.getOutputStream());
                    System.out.println("Server reading from channel");
                    int numOfTask;
                    int action=in.read();
                  
             if(action==DELETE)
              {
                 
                    numOfTask=in.read();
                    System.out.println("DELETE from Log - Task№"+numOfTask);
                    Server.controller.deleteTask(numOfTask);
                    out.write(DELETE_SUCCEFUL);
                    
              }
             if(action==CREATE)
             {
                 System.out.println("CREATE new Task in Log");
                 ObjectInputStream objectIn = new ObjectInputStream(in);
                 TaskNode newTask= (TaskNode)objectIn.readObject();
                 Server.controller.createTask(newTask);
                 out.write(CREATE_SUCCEFUL);
             }
                   
                    
                    if(action==Server.EXIT)
                    {
                    out.writeInt(SOCKET_CLOSE);
                    System.out.println("client closed");
                    clientSocket.close();
                    in.close();
                    out.close();
                    }
                    } catch (IOException ex) {
                            Logger.getLogger(ThreadServerPart.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
        Logger.getLogger(ThreadServerPart.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ParseException ex) {
        Logger.getLogger(ThreadServerPart.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    


    
}
    

