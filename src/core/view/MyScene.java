package core.view;

import Sample.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.util.HashMap;

public class MyScene {


    private HashMap<String, Scene> scenes = new HashMap<>();

    //Add the scene to the collection
    public void addScene(String name, Scene scene){
        scenes.put(name,scene);

    }

    //returns the Scene with the appropriate name
    public Scene getScene(String name){
        return scenes.get(name);
    }

    //Loads the fxml file, add the scene to the scenes collection
    //and finally injects the scenePane to the controller
    public boolean loadScreen(String name, String resource){
        try{
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScene = (Parent) myLoader.load();
            Scene scene = new Scene(loadScene);
            addScene(name, scene);
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }


    //This method tries to display the screen with a predefined name.
    public boolean setScene(String name){
        if(scenes.get(name) != null){ //scene loaded
            //TODO: make opacity etc
            Main.window.setScene(scenes.get(name));
            Main.window.centerOnScreen();
            Main.window.show();
            return true;
        }else {
            System.out.println("scene hasn't been loaded!!! \n");
            return false;
        }
    }

    public boolean unloadScene(String name){
        if(scenes.remove(name)==null){
            System.out.println("Scene didn't exist");
            return false;
        }else {
            return true;
        }
    }
}
