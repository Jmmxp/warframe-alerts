/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warframe.alerts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Justin
 */
public class WarframeAlerts extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        Image icon = new Image(getClass().getResourceAsStream("Icon.jpg"));
        //http://stackoverflow.com/questions/20732100/javafx-why-does-stage-setresizablefalse-cause-additional-margins
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setScene(scene);

        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
