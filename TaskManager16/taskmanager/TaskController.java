/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;

import java.io.IOException;
import java.text.ParseException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * @author Rodion
 */
public class TaskController {
    private Log tl;
    private View ui;

    public TaskController() throws ClassNotFoundException, IOException, SAXException, ParserConfigurationException, ParseException {
        this.tl = new TaskLog();
        this.ui = new View();

    }

    public Log getLog() {

        return tl;

    }

    public void mainView() {
        ui.mainView();
    }

    public boolean createTask(TaskNode newTask) throws IOException, ParseException {
        if (!newTask.getTaskName().isEmpty() && !newTask.getTaskDescription().isEmpty() && !newTask.getPhoneNumber().isEmpty()) {
            tl.createTask(newTask);
            tl.saveAll();
            return true;
        } else
            return false;

    }

    public boolean deleteTask(int index) throws IOException {

        if (tl.size() > index && index >= 0) {
            this.tl.deleteTask(tl.get(index));
            tl.saveAll();
            return true;
        } else
            return false;
    }

    public void viewAll() {
        if (!this.tl.isEmpty())
            ui.viewAllTasks(tl);

    }

    public void notification() throws IOException, ClassNotFoundException, ParseException {
        new TimeNotification(tl).onTimeNotification();
    }

    public void clear() {
        ui.clear();
    }
}
