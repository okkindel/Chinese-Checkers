package core.view.controllers;

import Sample.Main;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayersScene implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void NextButtonClicked(){
        Main.mainContainer.setScene(Main.PlayingSceneID);
    }

    public void BackButtonClicked(){
        Main.mainContainer.setScene(Main.NumberOfPlayersSceneID);
    }
}