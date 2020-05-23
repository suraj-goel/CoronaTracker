package controllers;

import com.jfoenix.controls.JFXTextField;


import data.CovidGeneral;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mainClasses.App;
import request.CovidStats;
import request.Response;

import java.io.IOException;

public class Controller_CovidGeneral {
    public JFXTextField total, confirmed, discharged, deaths;

    public JFXTextField state;
    public JFXTextField statelabel;
    private CovidGeneral covidGeneral;
    public void initialize() {
        CovidStats covidStats =new CovidStats();
        try {
            App.oosTracker.writeObject(covidStats);
            App.oosTracker.flush();
            Response response = (Response) App.oisTracker.readObject();
            if (response.getResponseCode().equals("success")) {
                 covidGeneral = (CovidGeneral) response.getResponseObject();
                total.setText(String.valueOf(covidGeneral.getTotalCases()));
                confirmed.setText(String.valueOf(covidGeneral.getConfirmedIndians()+covidGeneral.getForeigners()));
                discharged.setText((String.valueOf(covidGeneral.getDischarged())));
                deaths.setText((String.valueOf(covidGeneral.getDeaths())));
            } else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }
    public void onbackclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) total.getScene().getWindow();
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

    public void onupdateclicked(ActionEvent actionEvent) {
        CovidStats covidStats =new CovidStats();
        try {
            App.oosTracker.writeObject(covidStats);
            App.oosTracker.flush();
            Response response = (Response) App.oisTracker.readObject();
            if (response.getResponseCode().equals("success")) {
                 covidGeneral = (CovidGeneral) response.getResponseObject();
                total.setText(String.valueOf(covidGeneral.getTotalCases()));
                confirmed.setText(String.valueOf(covidGeneral.getConfirmedIndians()+covidGeneral.getForeigners()));
                discharged.setText((String.valueOf(covidGeneral.getDischarged())));
                deaths.setText((String.valueOf(covidGeneral.getDeaths())));
            } else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onsearchclicked(ActionEvent actionEvent) {
        String loc = state.getText();
        if(covidGeneral.mp.containsKey(loc)){
            statelabel.setText(String.valueOf(covidGeneral.mp.get(loc)));
        }else{
            statelabel.setText("state incorrect");
        }
    }
}
