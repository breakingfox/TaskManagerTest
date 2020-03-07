/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;


import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Rodion
 */
public class TaskManager implements Serializable {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     * @throws java.text.ParseException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException, InterruptedException, SAXException, ParserConfigurationException {
        // TODO code application logic here
        UserInterface TaskManager=new UserInterface();
        TaskManager.mainUserInterface();
}
    
}
