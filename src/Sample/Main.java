package Sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Optional;


public class Main extends Application {

    //public static Group root = new Group();
    public static Scene startingScene, choosingScene, playingScene, numberOfPlayersScene, playersScene;
    public static Stage window;

    public static void main(String[] args) {
        //new Menu();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        Parent startingSceneParent = FXMLLoader.load(getClass().getResource("/core/view/fxml/StartingScene.fxml"));
        startingScene = new Scene(startingSceneParent);

        Image icon = new Image(getClass().getResourceAsStream("/assets/icon.png"));
        primaryStage.getIcons().add(icon);
        //window.getStylesheets().add("Sample/test.css");

        window.setTitle("Chinese-checkers");
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        window.setScene(startingScene);
        window.setResizable(false);
        window.show();
    }

    public static void closeProgram(){
        //TODO: save data to enable coming back to the game
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Do you really want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            window.close();
        }

    }

}