/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Rodion
 */
public class TaskNode implements Serializable {
    private String TaskName;
    private String TaskDescription;
    private GregorianCalendar TaskDate;
    private String phoneNumber;
    private boolean changed;
    public TaskNode(String name,String description,GregorianCalendar date,String number)
    {
        TaskName=name;
        TaskDescription=description;
        TaskDate=date;
        phoneNumber=number;
        changed=false;
    }
    public boolean getChanged()
    {
        return this.changed;
    }
    public void setChanged(boolean c)
    {
        this.changed=c;
        
    }
    public String getTaskName()
    {
        return this.TaskName;
    }
     public String getTaskDescription()
    {
        return this.TaskDescription;
    }
     public GregorianCalendar getTaskDate()
     {
         return this.TaskDate;
     }
     public String getPhoneNumber()
    {
        return this.phoneNumber;
    }
     public void setTaskName(String name)
     {
         this.TaskName=name;
     }
     public void setTaskDescription(String description)
     {
         this.TaskDescription=description;
     }
     public void setTaskDate(GregorianCalendar date)
     {
         this.TaskDate=date;
     }
     public void setPhoneNumber(String number)
     {
         this.phoneNumber=number;
     }
}
