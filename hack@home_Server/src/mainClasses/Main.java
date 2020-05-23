package mainClasses;

import apiCalls.Covid19Stats;
import tools.MysqlConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.sql.Connection;
import java.util.UUID;

public class Main {

    public static String dbUser = "root";
    public static String dbPassword = "root";
    public static  String dbHost = "jdbc:mysql://localhost:3306/Hack@home";
    public static Connection connection = MysqlConnection.connect();
    public static final String generateUid(){
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }

    public static void main(String[] args) {


        ServerSocket serverSocket;
        Socket socket;
        try{
            serverSocket = new ServerSocket(6969);
            System.out.println("Server Started");
        }catch (IOException e){
            e.printStackTrace();
            return;
        }
        while (true){
            try{
                socket = serverSocket.accept();
                Thread t = new Thread(new HandleClientRequest(socket));
                t.start();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
