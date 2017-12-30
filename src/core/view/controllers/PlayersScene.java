package core.view.controllers;

import Sample.Main;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayersScene implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(NumberOfPlayersScene.choice == 6){
            System.out.println("six");
        }else if(NumberOfPlayersScene.choice == 4){
            System.out.println("four");
        }else if(NumberOfPlayersScene.choice == 3){
            System.out.println("three");
        }else if(NumberOfPlayersScene.choice == 2){
            System.out.println("two");
        }

    }

    public void NextButtonClicked(){

    }

    public void BackButtonClicked(){
        Main.window.setScene(Main.numberOfPlayersScene);
        Main.window.setResizable(false);
        Main.window.show();
    }
}