package core.view;

import Sample.Main;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MySceneTest {
    MyScene myScene;
    Scene scene;
    Parent root;
    Stage stage;
    @Before
    public void create(){
        myScene = new MyScene();
        root = mock(Parent.class);
    }

    @Test
    public void addScene(){
        scene = new Scene(root, 600, 600);
        myScene.addScene("scene", scene);
        assertEquals("scene" , myScene.getScene("scene"));
    }

    @Test
    public void getScene(){
        scene = new Scene(root, 600, 600);
        myScene.addScene("scene", scene);
        assertEquals("scene" , myScene.getScene("scene"));
    }

    @Test
    public void loadScreen() throws Exception {



//        myScene.loadScreen(Main.StartingSceneID, Main.StartingSceneFile);
//        myScene.setScene(Main.StartingSceneID, Main.window);
    }

    @Test
    public void setScene() throws Exception {
    }

}