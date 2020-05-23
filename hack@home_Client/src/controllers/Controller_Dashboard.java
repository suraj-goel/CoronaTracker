package controllers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import data.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import mainClasses.App;
import request.Response;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

/**
 *  Dashboard for the user after login where he/she can see his/her profile and All the files he/she has downloaded or shared on Distribution
 */
public class Controller_Dashboard {

    private String userUID;
    @FXML
    public JFXListView downloadedfiles, sharedfiles;
    public JFXTextField firstname, lastname, email, phone;
    public JFXButton sharefile, download, logout;


    /**
     * This will set the details of a user as soon as the scene is rendered
     */
    public void initialize() {
        firstname.setText(App.user.getFirstName());
        lastname.setText(App.user.getLastName());
        email.setText(App.user.getEmail());
        phone.setText(App.user.getPhone());
        userUID = App.user.getUserUID();


    }


    public void onlogoutclicked(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void ongeneralClicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) firstname.getScene().getWindow();
                Parent root = null;
                try {

                    root = FXMLLoader.load(getClass().getResource("/resources/covidgenral.fxml"));
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 1303, 961));

            }
        });
    }

    public void onpatientclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) firstname.getScene().getWindow();
                Parent root = null;
                try {

                    root = FXMLLoader.load(getClass().getResource("/resources/covidpatients.fxml"));
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 1303, 961));

            }
        });
    }

    public void onlatestclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) firstname.getScene().getWindow();
                Parent root = null;
                try {

                    root = FXMLLoader.load(getClass().getResource("/resources/covidnews.fxml"));
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 1303, 961));

            }
        });
    }
}
