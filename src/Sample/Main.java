package Sample;

import core.view.Menu;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Optional;

import core.view.MyScene;

public class Main extends Application {

    //public static Group root = new Group();

    public static Stage window;
    public static MyScene mainContainer;
    public static String StartingSceneID = "start";
    public static String StartingSceneFile = "/core/view/fxml/StartingScene.fxml";
    public static String ChoosingSceneID = "choose";
    public static String ChoosingSceneFile = "/core/view/fxml/ChoosingScene.fxml";
    public static String PlayingSceneID = "play";
    public static String PlayingSceneFile = "/core/view/fxml/PlayingScene.fxml";
    public static String PlayersSceneID = "players";
    public static String PlayersSceneFile = "/core/view/fxml/PlayersScene.fxml";
    public static String NumberOfPlayersSceneID = "numberOfPlayers";
    public static String NumberOfPlayersSceneFile = "/core/view/fxml/NumberOfPlayersScene.fxml";
    public static String RulesSceneID = "rules";
    public static String RulesSceneFile = "/core/view/fxml/RulesScene.fxml";


    public static void main(String[] args) {
        //new Menu();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        mainContainer = new MyScene();
        Image icon = new Image(getClass().getResourceAsStream("/assets/icon.png"));
        window.getIcons().add(icon);
        //window.getStylesheets().add("Sample/test.css");
        window.setResizable(false);
        window.setTitle("Chinese-checkers");
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        mainContainer.loadScreen(Main.StartingSceneID, Main.StartingSceneFile);
        mainContainer.loadScreen(Main.ChoosingSceneID, Main.ChoosingSceneFile);
        mainContainer.loadScreen(Main.RulesSceneID, Main.RulesSceneFile);
        mainContainer.loadScreen(Main.PlayingSceneID, Main.PlayingSceneFile);
        mainContainer.loadScreen(Main.PlayersSceneID, Main.PlayersSceneFile);
        mainContainer.loadScreen(Main.NumberOfPlayersSceneID, Main.NumberOfPlayersSceneFile);


        mainContainer.setScene(Main.StartingSceneID, window);

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