package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import data.CovidNews;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mainClasses.App;

import request.CovidNewsRequest;
import request.Response;

import java.io.IOException;

public class Controller_CovidNews {
    public JFXButton back;
    public JFXTextArea newsarea;
    public JFXTextField country;
    public JFXTextField source;
    private CovidNews covidNews;
    public void onbackclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) back.getScene().getWindow();
                Parent root = null;
                try {

                    root = FXMLLoader.load(getClass().getResource("/resources/dashboard.fxml"));
                }catch(IOException e){
                    e.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 1303, 961));

            }
        });
    }

    public void ongetnewsclicked(ActionEvent actionEvent) {
        String loc = country.getText();
        String sour = source.getText();
        CovidNewsRequest covidNewsRequest = new CovidNewsRequest(loc,sour);
        try {
            App.oosTracker.writeObject(covidNewsRequest);
            App.oosTracker.flush();
            Response response = (Response) App.oisTracker.readObject();
            if (response.getResponseCode().equals("success")) {
                covidNews = (CovidNews) response.getResponseObject();
                StringBuilder sb = new StringBuilder("Headlines\n");
                for(int i=0;i<covidNews.title.size();i++){
                    String title = covidNews.title.get(i)+"\n";
                    sb.append(title);
                }
                System.out.println(sb);
                newsarea.setText(sb.toString());

            } else {


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
