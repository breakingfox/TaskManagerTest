package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {

    private Stage mainStage;

    public void setMainStage (Stage mainStage) { this.mainStage = mainStage;}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void actionButtonPressed(javafx.event.ActionEvent actionEvent) throws IOException {
        Object sourse = actionEvent.getSource();
        if(!(sourse instanceof Button)) return;

        Button clickButton = (Button) sourse;
        String title = "";
        switch (clickButton.getId()){
            case "btnAddTask":
                Stage newStage = new Stage();
                FXMLLoader newLoader = new FXMLLoader();
                newStage.setTitle("AddTasks");
                URL xmlUrl = getClass().getResource("AddTask.fxml");
                newLoader.setLocation(xmlUrl);
                Parent newRoot = newLoader.load();
                Scene newScene = new Scene(newRoot);
                newStage.setScene(newScene);
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.show();
                break;
            case "btnDelTask":


                break;
            case "btnAllTask":

                break;
        }
    }
}
