package com.github.metakol.testtask.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;

public class Scenes {
    public static void sceneChange(EventObject event, URL FXMLFile){
        try{
            FXMLLoader loader=new FXMLLoader(FXMLFile);
            Scene scene=new Scene(loader.load());
            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
