package core.view.controllers;

import Sample.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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
        Main.mainContainer.setScene(Main.NumberOfPlayersSceneID);
    }

    @FXML
    public void RulesButtonClicked(){
        Main.mainContainer.setScene(Main.RulesSceneID);
    }

    @FXML
    public void ReturnButtonClicked(){
        Main.mainContainer.setScene(Main.StartingSceneID);
    }



}