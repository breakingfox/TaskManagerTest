/*
       * To change this license header, choose License Headers in Project Properties.
        * To change this template file, choose Tools | Templates
        * and open the template in the editor.
        */
        package taskmanager;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.IOException;
        import java.io.ObjectInputStream;
        import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
        import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
        import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
        

/**
 *
 * @author Rodion
 */
public class TaskLog implements Serializable,Log<TaskNode>{
    public static String path="file.xml";
    private ArrayList <TaskNode> TaskList;
    public TaskLog() throws IOException, ClassNotFoundException, SAXException, ParseException, ParserConfigurationException {
        
        
 
        File xmlFile = new File(path);
        if (xmlFile.exists()) {
            if (xmlFile.length() != 0) {
                ArrayList <TaskNode> tl=new ArrayList <TaskNode>();
                
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Корневой элемент: " + doc.getDocumentElement().getNodeName());
            
            NodeList nodeList = doc.getElementsByTagName("Tasks");
         for (int i = 0; i < nodeList.getLength(); i++) {
               tl.add(getTaskForRead(nodeList.item(i)));
            }
         this.TaskList=tl;
            } else {
                this.TaskList = new ArrayList<TaskNode>();
            }
        } else {
            if (xmlFile.createNewFile()) {
                this.TaskList = new ArrayList<TaskNode>();
            } else {
                System.out.printf("Error");
            }
        }
    }
     private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
      private static TaskNode getTaskForRead(Node node) throws ParseException {
      
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            
           Element element = (Element) node;

           
           int year,month,day,hour,minute;
           year=Integer.valueOf(getTagValue("year", element));
           month=Integer.valueOf(getTagValue("month", element));
           day=Integer.valueOf(getTagValue("day", element));
           hour=Integer.valueOf(getTagValue("hour", element));
           minute=Integer.valueOf(getTagValue("minute", element));
           GregorianCalendar cal = new GregorianCalendar(year,month,day,hour,minute);
           
           return new TaskNode(getTagValue("name", element),getTagValue("description", element),cal,getTagValue("number", element));
            
        }
        return null;
 
       
    }
    @Override
    public void createTask(TaskNode task)
    {
        this.TaskList.add(task);
    }
    @Override
    public void deleteTask(TaskNode object) throws IllegalArgumentException
    {
        this.TaskList.remove(object);
    }
    private static Node getTaskForWrite(Document doc, String id, String name, String description,GregorianCalendar date,String number) {
        Element task = doc.createElement("Task");
 
        // устанавливаем атрибут id
        task.setAttribute("id", id);
 
       
        task.appendChild(getTaskElements(doc, task, "name", name));
 
      
        task.appendChild(getTaskElements(doc, task, "description", description));
        Date d;
        
        task.appendChild(getTaskElements(doc, task, "year", Integer.toString(date.get(Calendar.YEAR))));
         task.appendChild(getTaskElements(doc, task, "month", Integer.toString(date.get(Calendar.MONTH))));
          task.appendChild(getTaskElements(doc, task, "day", Integer.toString(date.get(Calendar.DAY_OF_MONTH))));
           task.appendChild(getTaskElements(doc, task, "hour", Integer.toString(date.get(Calendar.HOUR))));
            task.appendChild(getTaskElements(doc, task, "minute", Integer.toString(date.get(Calendar.MINUTE))));
        
        task.appendChild(getTaskElements(doc, task, "number", number));
        return task;
    }
    private static Node getTaskElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
    @Override
    public void saveAll() throws IOException {
       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            
            // создаем пустой объект Document, в котором будем 
            // создавать наш xml-файл
            Document doc = builder.newDocument();
            // создаем корневой элемент
            Element rootElement =
            doc.createElementNS(" ", "Tasks");
            // добавляем корневой элемент в объект Document
            doc.appendChild(rootElement);
 
            // добавляем первый дочерний элемент к корневому
            for(int i=0;i<TaskList.size();i++)
            {
            rootElement.appendChild(getTaskForWrite(doc, Integer.toString(i), TaskList.get(i).getTaskName(),  TaskList.get(i).getTaskDescription(), TaskList.get(i).getTaskDate(), TaskList.get(i).getPhoneNumber()));
            }
 
           
 
            //создаем объект TransformerFactory для печати в консоль 
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // для красивого вывода в консоль
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
 
            //печатаем в консоль или файл
           
            StreamResult file = new StreamResult(getFile());
 
            //записываем данные
            
            transformer.transform(source, file);
           
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
public static File getFile() throws IOException
{
            File f;
            while (true) {
                 f = new File(path);
                if (!f.exists()) 
                    {
                         if(!f.createNewFile())
                         {
                            
                          Scanner s = new Scanner(System.in);
                          System.out.println("write error");
                          System.out.println("enter another path - 1,exit - 0");
                          int change=s.nextInt();
                          if(change==0)
                          {
                          System.out.println("enter path: ");
                          path = s.nextLine();
                          }
                          if(change==1)
                          {
                              System.exit(0);
                          }
                             
                         }
                    }
                else
                {
                    f.delete();
                    f.createNewFile();
                    return f;

                }
               
            }
        
         
}
    @Override
    public TaskNode get(int i) {
        return this.TaskList.get(i);
    }

    @Override
    public int size() {
       return this.TaskList.size();
    }

    @Override
    public boolean isEmpty() {
       return this.TaskList.isEmpty();
    }
}