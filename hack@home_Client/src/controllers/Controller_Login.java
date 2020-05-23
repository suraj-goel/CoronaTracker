package controllers;
/**
 *  Server Client Login Service
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import data.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mainClasses.App;
import request.LoginRequest;
import request.Response;
import tools.HashGenerator;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Controller_Login {
    public JFXTextField email;
    public JFXPasswordField password;
    public Label status;

    @FXML
    JFXButton login,signup;

    /**
     *  New Customer Signup
     * @param actionEvent
     */
    public void onsignupclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) signup.getScene().getWindow();
                Parent root = null;
                try {

                    root = FXMLLoader.load(getClass().getResource("/resources/signup.fxml"));
                }catch(IOException e){
                    e.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 1081, 826));

            }
        });
    }

    /**
     * Sending LoginRequest to Server and Recieving the Response
     * @param actionEvent
     */
    public void onloginclicked(ActionEvent actionEvent)
    {
        String emailid = this.email.getText();
        String password = this.password.getText();
        if(password==null){
            status.setText("Password : Null");
        }
        else{
            LoginRequest loginRequest = new LoginRequest(emailid, HashGenerator.hash(password));
            new Thread(new Runnable(){
                @Override
                public void run(){
                    try{
                        App.sockerTracker = new Socket(App.serverIP,App.portNo);
                        App.oosTracker = new ObjectOutputStream(App.sockerTracker.getOutputStream());
                        App.oisTracker = new ObjectInputStream(App.sockerTracker.getInputStream());
                        App.oosTracker.writeObject(loginRequest);
                        App.oosTracker.flush();
                        Response response;
                        response = (Response)App.oisTracker.readObject();
                        App.user = (User)response.getResponseObject();
                        if(response.getResponseCode().equals("success")){
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Stage primaryStage = (Stage) signup.getScene().getWindow();
                                    Parent root = null;
                                    try {

                                        root = FXMLLoader.load(getClass().getResource("/resources/dashboard.fxml"));
                                    }
                                    catch(IOException e){
                                        e.printStackTrace();
                                    }
                                    primaryStage.setScene(new Scene(root, 1303, 961));

                                }
                            });
                        }
                        else{
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    status.setText("Error");
                                }
                            });
                        }

                    }
                    catch (IOException | ClassNotFoundException e){

                    }
                }
            }).start();
        }

    }

    public void onexitclicked(ActionEvent actionEvent) {
        System.exit(0);
    }
}
