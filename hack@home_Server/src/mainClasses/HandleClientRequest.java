package mainClasses;

import apiCalls.Covid19Stats;
import apiCalls.CovidNewsCall;
import authenticationHandler.Login;
import authenticationHandler.SignUp;
import request.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class HandleClientRequest extends Thread{
    private Socket socket ;
    ObjectOutputStream oos;
    ObjectInputStream ois ;
    public HandleClientRequest(Socket socket){
        this.socket=socket;
        System.out.println(socket.getInetAddress().getCanonicalHostName());
        try{
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
//            userIP = socket.getInetAddress().getCanonicalHostName();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        Request request = null;
        while(true){
            try{

                try{
                    Object object=ois.readObject();
                    System.out.println(object.getClass());
                    request = (Request)object;
                }catch (EOFException e){
                    System.out.println("Client Disconnected..!!");
                    return;
                }catch (SocketException e){
                    System.out.println("Client Disconnected..!!");
                    return;
                }

                if(request.getRequestCode().equals("signup")) {
                    SignUp signUp = new SignUp((SignupRequest) request);
                    Response response = signUp.insert();
                    oos.writeObject(response);
                    oos.flush();
                }else if(request.getRequestCode().equals("login")){
                    Login login = new Login((LoginRequest)request);
                    Response response = login.getResponse();
                    oos.writeObject(response);
                    oos.flush();
                }else if(request.getRequestCode().equals("covidgeneral")){
                    Covid19Stats covid19Stats = new Covid19Stats();
                    Response response = covid19Stats.getResponse();
                    oos.writeObject(response);
                    oos.flush();
                }else if(request.getRequestCode().equals("covidnews")){
                    CovidNewsCall covidNewsCall = new CovidNewsCall((CovidNewsRequest)request);
                    Response response = covidNewsCall.getResponse();
                    oos.writeObject(response);
                    oos.flush();
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }
}

