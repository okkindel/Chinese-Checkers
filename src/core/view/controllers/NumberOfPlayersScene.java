package core.view.controllers;

import Sample.Main;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NumberOfPlayersScene implements Initializable {

    static int choice;
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
        Main.mainContainer.setScene(Main.ChoosingSceneID);
    }

    private void getChoice(ChoiceBox<Integer> choiceBox){
        choice = choiceBox.getValue();

        if(NumberOfPlayersScene.choice == 6){
            System.out.println("six");
            PlayersScene.playersScene.label1.setVisible(true);
            PlayersScene.playersScene.label2.setVisible(true);
            PlayersScene.playersScene.label3.setVisible(true);
            PlayersScene.playersScene.label4.setVisible(true);
            PlayersScene.playersScene.label5.setVisible(true);
            PlayersScene.playersScene.label6.setVisible(true);
            PlayersScene.playersScene.ChoiceBox1.setVisible(true);
            PlayersScene.playersScene.ChoiceBox2.setVisible(true);
            PlayersScene.playersScene.ChoiceBox3.setVisible(true);
            PlayersScene.playersScene.ChoiceBox4.setVisible(true);
            PlayersScene.playersScene.ChoiceBox5.setVisible(true);
            PlayersScene.playersScene.ChoiceBox6.setVisible(true);
        }else if(NumberOfPlayersScene.choice == 4){
            System.out.println("four");
            PlayersScene.playersScene.label1.setVisible(true);
            PlayersScene.playersScene.label2.setVisible(true);
            PlayersScene.playersScene.label3.setVisible(true);
            PlayersScene.playersScene.label4.setVisible(true);
            PlayersScene.playersScene.label5.setVisible(false);
            PlayersScene.playersScene.label6.setVisible(false);
            PlayersScene.playersScene.ChoiceBox1.setVisible(true);
            PlayersScene.playersScene.ChoiceBox2.setVisible(true);
            PlayersScene.playersScene.ChoiceBox3.setVisible(true);
            PlayersScene.playersScene.ChoiceBox4.setVisible(true);
            PlayersScene.playersScene.ChoiceBox5.setVisible(false);
            PlayersScene.playersScene.ChoiceBox6.setVisible(false);
        }else if(NumberOfPlayersScene.choice == 3){
            System.out.println("three");
            PlayersScene.playersScene.label1.setVisible(true);
            PlayersScene.playersScene.label2.setVisible(true);
            PlayersScene.playersScene.label3.setVisible(true);
            PlayersScene.playersScene.label4.setVisible(false);
            PlayersScene.playersScene.label5.setVisible(false);
            PlayersScene.playersScene.label6.setVisible(false);
            PlayersScene.playersScene.ChoiceBox1.setVisible(true);
            PlayersScene.playersScene.ChoiceBox2.setVisible(true);
            PlayersScene.playersScene.ChoiceBox3.setVisible(true);
            PlayersScene.playersScene.ChoiceBox4.setVisible(false);
            PlayersScene.playersScene.ChoiceBox5.setVisible(false);
            PlayersScene.playersScene.ChoiceBox6.setVisible(false);
        }else if(NumberOfPlayersScene.choice == 2){
            System.out.println("two");
            PlayersScene.playersScene.label1.setVisible(true);
            PlayersScene.playersScene.label2.setVisible(true);
            PlayersScene.playersScene.label3.setVisible(false);
            PlayersScene.playersScene.label4.setVisible(false);
            PlayersScene.playersScene.label5.setVisible(false);
            PlayersScene.playersScene.label6.setVisible(false);
            PlayersScene.playersScene.ChoiceBox1.setVisible(true);
            PlayersScene.playersScene.ChoiceBox2.setVisible(true);
            PlayersScene.playersScene.ChoiceBox3.setVisible(false);
            PlayersScene.playersScene.ChoiceBox4.setVisible(false);
            PlayersScene.playersScene.ChoiceBox5.setVisible(false);
            PlayersScene.playersScene.ChoiceBox6.setVisible(false);
        }
        Main.mainContainer.setScene(Main.PlayersSceneID);
    }
}