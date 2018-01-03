package core.view.controllers;

import Sample.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;


import java.net.URL;
import java.util.ResourceBundle;

public class RulesScene implements Initializable {


    public WebView browser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            browser.getEngine().load("http://daa.pl/DPs");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void BackButtonClicked(){
        Main.mainContainer.setScene(Main.ChoosingSceneID, Main.window);
    }
}