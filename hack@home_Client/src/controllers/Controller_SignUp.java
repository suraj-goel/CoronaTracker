package controllers;
/**
 *  Sign up Service for a new Customer
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import data.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import mainClasses.App;
import request.Response;

import request.SignupRequest;
import tools.HashGenerator;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Controller_SignUp {

    public JFXTextField email;
    public JFXButton login;
    public JFXButton back;
    public JFXTextField firstname;
    public JFXTextField lastname;
    public Label status;
    public JFXTextField contact;
    public JFXPasswordField password;

    public void onsubmitclicked(ActionEvent actionEvent) {
        User user = new User();
        user.setEmail(email.getText());
        user.setFirstName(firstname.getText());
        user.setLastName(lastname.getText());
        user.setPhone(contact.getText());
        user.setUserUID(App.generateUid());
        try{
            if(App.sockerTracker == null){
                App.sockerTracker = new Socket(App.serverIP, App.portNo);
                App.oosTracker = new ObjectOutputStream(App.sockerTracker.getOutputStream());
                App.oisTracker = new ObjectInputStream(App.sockerTracker.getInputStream());
            }
            SignupRequest signUpRequest = new SignupRequest(HashGenerator.hash(password.getText()),user);
            System.out.println(HashGenerator.hash(password.getText()));
            App.oosTracker.writeObject(signUpRequest);
            App.oosTracker.flush();
            Response response = (Response)App.oisTracker.readObject();
            if(response.getResponseCode().equals("success")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        status.setText("SignUp Successful");
                    }
                });
            }else{
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        status.setText("Error");
                    }
                });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onbackclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) back.getScene().getWindow();
                Parent root = null;
                try {

                    root = FXMLLoader.load(getClass().getResource("/resources/login.fxml"));
                }catch(IOException e){
                    e.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 1081, 826));

            }
        });
    }
}
