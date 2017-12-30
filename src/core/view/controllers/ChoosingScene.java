package core.view.controllers;

import Sample.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChoosingScene implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println("View is now loaded!");
    }

    @FXML
    public void PlayButtonClicked() throws IOException {
        Parent choosingSceneParent = FXMLLoader.load(getClass().getResource("/core/view/fxml/NumberOfPlayersScene.fxml"));
        Main.numberOfPlayersScene = new Scene(choosingSceneParent);
        Main.window.setScene(Main.numberOfPlayersScene);
        Main.window.show();
    }

    @FXML
    public void RulesButtonClicked(){
        //TODO: scene with rules of the game
    }

    @FXML
    public void ReturnButtonClicked(){
        Main.window.setScene(Main.startingScene);
        Main.window.setResizable(false);
        Main.window.show();
    }



}