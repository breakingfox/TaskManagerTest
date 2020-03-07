/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager.client;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import taskmanager.Message;
import taskmanager.server.ThreadServerPart;

/**
 * @author Rodion
 */
public class DeleteWindow extends JFrame {
    public DeleteWindow() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JTextField smallField = new JTextField(15);
        JButton deleteButton = new JButton("Удалить");

        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


                ObjectOutputStream objectOutput;
                try {
                    objectOutput = new ObjectOutputStream(Client.out);

                    objectOutput.writeObject(new Message(Integer.valueOf(smallField.getText()), "DELETE"));
                    objectOutput.flush();

                    int mes = Client.in.readInt();
                    if (mes == Client.DELETE_SUCCEFUL) {
                        JOptionPane.showMessageDialog(DeleteWindow.this,
                                "Событие удалено!");
                    } else
                        JOptionPane.showMessageDialog(DeleteWindow.this,
                                "Ошибка удаления события");
                    dispose();
                } catch (IOException ex) {
                }
            }
        });
        JPanel contents = new JPanel(new FlowLayout(FlowLayout.LEFT));
        contents.add(smallField);
        contents.setLayout(new FlowLayout(FlowLayout.CENTER));
        contents.add(deleteButton);
        setContentPane(contents);
        setSize(200, 150);
        setVisible(true);
    }

}
