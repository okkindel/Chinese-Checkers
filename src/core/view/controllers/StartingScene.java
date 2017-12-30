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
    public void StartGameButtonHandler() throws IOException {
        Parent choosingSceneParent = FXMLLoader.load(getClass().getResource("/core/view/fxml/ChoosingScene.fxml"));
        Main.choosingScene = new Scene(choosingSceneParent);
        Main.window.setScene(Main.choosingScene);
        Main.window.show();
    }

    public void RulesButtonHandler(){

    }

    @FXML
    public void ExitGameButtonHandler(){
        ExitGameButton.setOnAction(e -> closeProgram());
    }
}

//public static Group root = new Group();
//root.getChildren().add(FXMLLoader.load(getClass().getResource("StartingScene.fxml")));