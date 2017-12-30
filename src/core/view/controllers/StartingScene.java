package core.view.controllers;

import Sample.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Sample.Main.closeProgram;


public class StartingScene implements Initializable {

    public Button ExitGameButton = new Button();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println("View is now loaded!");
    }

    @FXML
    public void StartGameButtonHandler(){
        Main.mainContainer.setScene(Main.ChoosingSceneID);
    }

    @FXML
    public void ExitGameButtonHandler(){
         closeProgram();
    }
}

//public static Group root = new Group();
//root.getChildren().add(FXMLLoader.load(getClass().getResource("StartingScene.fxml")));