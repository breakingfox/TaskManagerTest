/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import taskmanager.Log;
import taskmanager.TaskNode;

/**
 * @author Rodion
 */
public class ShowWindow extends JFrame {
    private Log<TaskNode> log;
    public static int index;

    public ShowWindow(Log l) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.log = l;
        JTextField name = new JTextField(log.get(index).getTaskName());

        JTextField description = new JTextField(log.get(index).getTaskDescription());


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss.sss Z");
        String formattedDate = formatter.format(((GregorianCalendar) log.get(index).getTaskDate()).getTime());
        JTextField date =
                new JTextField(formattedDate);

        JTextField contacts =
                new JTextField(log.get(index).getPhoneNumber());
        JPanel contents = new JPanel();
        contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
        contents.add(new JLabel("TASK :"));
        contents.add(new JLabel("Name"));
        contents.add(name);
        contents.add(new JLabel("Description"));
        contents.add(description);
        contents.add(new JLabel("Date"));
        contents.add(date);
        contents.add(new JLabel("Contacts"));
        contents.add(contacts);

        JButton buttonNext = new JButton("NEXT");
        JButton buttonBack = new JButton("BACK");
        if (index == 0)
            buttonBack.setEnabled(false);
        if (index == log.size() - 1)
            buttonNext.setEnabled(false);
        buttonNext.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                index++;
                new ShowWindow(log);
            }


        });
        buttonBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                index--;
                new ShowWindow(log);
            }


        });
        contents.add(buttonNext);
        contents.add(buttonBack);
        getContentPane().add(contents);
        setSize(1450, 500);
        setVisible(true);
    }

}
    

