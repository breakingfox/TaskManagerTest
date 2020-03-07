/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.GregorianCalendar;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import taskmanager.Message;
import taskmanager.TaskNode;
import taskmanager.TimeNotification;
import taskmanager.server.ThreadServerPart;

/**
 * @author Rodion
 */
public class CreateWindow extends JFrame {

    public CreateWindow() {
        JTextField name =
                new JTextField(30);

        JTextField description =
                new JTextField(30);

        JTextField year =
                new JTextField(30);

        JTextField month =
                new JTextField(30);
        JTextField day =
                new JTextField(30);
        JTextField hour =
                new JTextField(30);
        JTextField minute =
                new JTextField(30);
        JTextField contacts =
                new JTextField(30);


        // Создание интерфейса окна
        JPanel contents = new JPanel();
        contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
        contents.add(new JLabel("NEW TASK :"));
        contents.add(new JLabel("Name"));
        contents.add(name);
        contents.add(new JLabel("Description"));
        contents.add(description);
        contents.add(new JLabel("Year"));
        contents.add(year);
        contents.add(new JLabel("Month"));
        contents.add(month);
        contents.add(new JLabel("Day"));
        contents.add(day);
        contents.add(new JLabel("Hour"));
        contents.add(hour);
        contents.add(new JLabel("Minute"));
        contents.add(minute);
        contents.add(new JLabel("Contacts"));
        contents.add(contacts);
        JButton buttonCreate = new JButton("Create");
        buttonCreate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                TaskNode task = new TaskNode(name.getText(), description.getText(), new GregorianCalendar(Integer.valueOf(year.getText()),
                        Integer.valueOf(month.getText()), Integer.valueOf(day.getText()), Integer.valueOf(hour.getText()), Integer.valueOf(minute.getText())), contacts.getText());
                ObjectOutputStream objectOutput;

                try {
                    objectOutput = new ObjectOutputStream(Client.out);

                    objectOutput.writeObject(new Message(task, "CREATE"));
                    objectOutput.flush();

                    int mes = Client.in.readInt();
                    if (mes == Client.CREATE_SUCCEFUL) {
                        JOptionPane.showMessageDialog(CreateWindow.this,
                                "Событие создано!");
                    } else
                        JOptionPane.showMessageDialog(CreateWindow.this,
                                "Ошибка создания события");
                    dispose();
                } catch (IOException ex) {
                }
            }
        });
        contents.add(buttonCreate);
        getContentPane().add(contents);
        setSize(1450, 500);
        setVisible(true);
    }

}
