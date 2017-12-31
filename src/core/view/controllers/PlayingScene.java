package core.view.controllers;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayingScene implements Initializable {

    public AnchorPane panel;
    Circle circle;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for(int i=0; i<60; i++){
            panel.getChildren().get(i).setOnMousePressed(NodeOnMousePressedEventHandler);
            panel.getChildren().get(i).setOnMouseDragged(NodeOnMouseDraggedEventHandler);
        }

//        circle = new Circle(10.5, Color.RED);
//        circle.setCenterX(50);
//        circle.setCenterY(50);
//        circle.setOnMousePressed(NodeOnMousePressedEventHandler);
//        circle.setOnMouseDragged(circleOnMouseDraggedEventHandler);
//        panel.getChildren().add(circle);
    }




    EventHandler<MouseEvent> NodeOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((Node)(t.getSource())).getTranslateX();
                    orgTranslateY = ((Node)(t.getSource())).getTranslateY();
                }
            };

    EventHandler<MouseEvent> NodeOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((Node)(t.getSource())).setTranslateX(newTranslateX);
                    ((Node)(t.getSource())).setTranslateY(newTranslateY);
                }
            };
}