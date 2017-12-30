package core.view.controllers;

import Sample.Main;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NumberOfPlayersScene implements Initializable {
    static int choice;
    public ChoiceBox<Integer> choiceBox = new ChoiceBox<>();
    public Button NextButton =  new Button();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBox.setValue(2);
        choiceBox.getItems().addAll(2,3,4,6);
    }

    public void NextButtonClicked() throws IOException {
        getChoice(choiceBox);
        Parent playersSceneParent = FXMLLoader.load(getClass().getResource("/core/view/fxml/PlayersScene.fxml"));
        Main.playersScene = new Scene(playersSceneParent);
        Main.window.setScene(Main.playersScene);
        Main.window.show();
    }

    public void BackButtonClicked() {
        Main.window.setScene(Main.choosingScene);
        Main.window.setResizable(false);
        Main.window.show();
    }

    private static void getChoice(ChoiceBox<Integer> choiceBox){
        choice = choiceBox.getValue();
        System.out.println(choice);
    }
}