package core.view.controllers;

import Sample.Main;
import core.model.Player;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlayersScene implements Initializable {
    public static PlayersScene playersScene;

    ArrayList<ChoiceBox<String>> BoxArray = new ArrayList<>();
    ArrayList<Label> LabelArray = new ArrayList<>();
    ArrayList<String> choices = new ArrayList<>();

    public ChoiceBox<String> ChoiceBox1 = new ChoiceBox<>();
    public ChoiceBox<String> ChoiceBox2 = new ChoiceBox<>();
    public ChoiceBox<String> ChoiceBox3 = new ChoiceBox<>();
    public ChoiceBox<String> ChoiceBox4 = new ChoiceBox<>();
    public ChoiceBox<String> ChoiceBox5 = new ChoiceBox<>();
    public ChoiceBox<String> ChoiceBox6 = new ChoiceBox<>();

    public Label label1 = new Label();
    public Label label2 = new Label();
    public Label label3 = new Label();
    public Label label4 = new Label();
    public Label label5 = new Label();
    public Label label6 = new Label();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playersScene = this;

        choices.add("Human");
        choices.add("Computer");
        choices.add("Empty");

        LabelArray.add(label1);
        LabelArray.add(label2);
        LabelArray.add(label3);
        LabelArray.add(label4);
        LabelArray.add(label5);
        LabelArray.add(label6);

        BoxArray.add(ChoiceBox1);
        BoxArray.add(ChoiceBox2);
        BoxArray.add(ChoiceBox3);
        BoxArray.add(ChoiceBox4);
        BoxArray.add(ChoiceBox5);
        BoxArray.add(ChoiceBox6);

        for(int j=0; j<6; j++) {
            for (int i = 0; i < choices.size(); i++) {
                BoxArray.get(j).getItems().add(choices.get(i));
            }
            BoxArray.get(j).setValue(choices.get(0));
        }
    }

    public void NextButtonClicked(){
        Main.mainContainer.setScene(Main.PlayingSceneID, Main.window);
    }

    public void BackButtonClicked(){
        Main.mainContainer.setScene(Main.NumberOfPlayersSceneID, Main.window);
    }

    public void hideCheckBox(int number){
        BoxArray.get(number).setVisible(false);
    }
}