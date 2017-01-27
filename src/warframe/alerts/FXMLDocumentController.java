/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warframe.alerts;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Justin
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Label infoLabel;
    @FXML
    private Label updateLabel;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        infoLabel.setStyle("-fx-font-weight: bold");
        try {
            // TODO
            fetchInfo();
        } catch (IOException | ParseException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        timer();

    }

    private void timer() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(60000),
                ae -> {
                    try {
                        fetchInfo();
                    } catch (IOException | ParseException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void fetchInfo() throws IOException, ParseException {

        SimpleDateFormat parserSDF = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
        String html = "http://content.warframe.com/dynamic/rss.php";
        Document doc = Jsoup.connect(html).get();
        Elements item = doc.select("item");
        infoLabel.setText("");
        for (Element i : item) {
            infoLabel.setText(infoLabel.getText() + i.select("author").text());
            infoLabel.setText(infoLabel.getText() + " - " + i.select("title").text());
            infoLabel.setText(infoLabel.getText() + " " + i.select(("description")).text() + " ");
            String expiry = i.select("wf|expiry").text();
            Date expDate = null;
            if (!(expiry.isEmpty())) {
                expDate = parserSDF.parse(expiry);
                long timeLeft = expDate.getTime() - System.currentTimeMillis();
                timeLeft /= 60000;
                infoLabel.setText(infoLabel.getText() + " - " + timeLeft + "m left");

            }
            infoLabel.setText(infoLabel.getText() + "\n\n");

//            String pubTime = i.select("pubDate").text().substring(18, 26);
//            String expTime = i.select("wf:expiry").text().substring(18, 26);
//            //infoLabel.setText((infoLabel.getText() + ));
//            System.out.println("pubTime is" + pubTime + "\n and expTime is " + expTime);
        }

    }
}

//    private void printUpdating() {
//        updateLabel.setText("Updating");
//        try {
//            Thread.sleep(2000);                 
//        } catch (InterruptedException ex) {
//            Thread.currentThread().interrupt();
//        }
//
//    } Lags the program when it sleeps. Taken out. (Isn't this how threads work? I think it stops the program)

