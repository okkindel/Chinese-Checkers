package core.view.controllers;

import Sample.Main;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NumberOfPlayersScene implements Initializable {


    public ChoiceBox<Integer> choiceBox = new ChoiceBox<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        choiceBox.setValue(2);
        choiceBox.getItems().addAll(2,3,4,6);
    }

    public void NextButtonClicked() throws IOException {
        getChoice(choiceBox);
    }

    public void BackButtonClicked() {
        Main.mainContainer.setScene(Main.ChoosingSceneID, Main.window);
    }

    private void getChoice(ChoiceBox<Integer> choiceBox){
        int choice = choiceBox.getValue();

        if(choice == 6){
            hidePlayers(6,6);
        }else if(choice == 4){
            hidePlayers(4,6);
        }else if(choice == 3){
            hidePlayers(3,6);
        }else if(choice == 2){
            hidePlayers(2,6);
        }
        Main.mainContainer.setScene(Main.PlayersSceneID, Main.window);
    }

    private void hidePlayers(int visibleNumberOfPlayers, int allPlayers){
        for(int i=0; i<allPlayers; i++){
            PlayersScene.playersScene.BoxArray.get(i).setVisible(true);
            PlayersScene.playersScene.LabelArray.get(i).setVisible(true);
        }
        for(int j=allPlayers-1; j>=visibleNumberOfPlayers; j--){
            PlayersScene.playersScene.BoxArray.get(j).setVisible(false);
            PlayersScene.playersScene.LabelArray.get(j).setVisible(false);
        }
    }
}