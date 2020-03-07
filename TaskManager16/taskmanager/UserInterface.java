package taskmanager;

import taskmanager.TaskLog;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Ilya
 */

public class UserInterface  implements Serializable {
    

    public UserInterface ()  {
       
    }

    public void mainUserInterface() throws IOException, ParseException, ClassNotFoundException, InterruptedException, SAXException, ParserConfigurationException {
        System.out.println("TASKMANAGER 0.11");
        Scanner in = new Scanner(System.in);
       
        int choice = -1;
        TaskController tc=new TaskController();
        
        while (true){
            
            choice = in.nextInt();
            switch (choice){
                case 1:{
                   tc.clear();
//                   tc.createTask();
                 
                    break;
                }
                case 2:{
                    tc.viewAll();
                   
                    break;
                }
                case 3:{
                    tc.clear();
//                    tc.deleteTask();
                   
                    break;
                }
                case 0: 
                {
                    tc.getLog().saveAll();
                    System.exit(0);
                
                }
            }
           
        
        }
    }
}
