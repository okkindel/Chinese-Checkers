package core.view.controllers;

import Sample.Main;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NumberOfPlayersScene implements Initializable {
    static int choice;
    public ChoiceBox<Integer> choiceBox = new ChoiceBox<>();
    public Button NextButton =  new Button();
    public Button BackButton = new Button();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBox.setValue(2);
        choiceBox.getItems().addAll(2,3,4,6);
    }

    public void NextButtonClicked() throws IOException {
        getChoice(choiceBox);
    }

    public void BackButtonClicked() {
        Main.mainContainer.setScene(Main.ChoosingSceneID);
    }

    private static void getChoice(ChoiceBox<Integer> choiceBox){
        choice = choiceBox.getValue();
        if(NumberOfPlayersScene.choice == 6){
            System.out.println("six");
        }else if(NumberOfPlayersScene.choice == 4){
            System.out.println("four");
        }else if(NumberOfPlayersScene.choice == 3){
            System.out.println("three");
        }else if(NumberOfPlayersScene.choice == 2){
            System.out.println("two");
        }
        Main.mainContainer.setScene(Main.PlayersSceneID);
    }
}