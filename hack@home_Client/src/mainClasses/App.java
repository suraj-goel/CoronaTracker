package mainClasses;

import data.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class App extends Application {
    public static String serverIP = "localhost";
    public static int portNo = 6969;
    public static Socket sockerTracker ;
    public static ObjectOutputStream oosTracker ;
    public static ObjectInputStream oisTracker;
    public static User user;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/resources/login.fxml"));
        primaryStage.setTitle("Hack@Home");
        primaryStage.setScene(new Scene(root, 1081, 826));
        primaryStage.show();
    }
    public static final String generateUid(){
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
