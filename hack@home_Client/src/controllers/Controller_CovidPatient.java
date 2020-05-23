package controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Controller_CovidPatient {
    public JFXTextField patient;
    public JFXTextArea details;
    private volatile JSONObject jsonObject;
    public void initialize(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {

                    String url="https://api.rootnet.in/covid19-in/unofficial/covid19india.org";
                    URL object=new URL(url);
                    HttpURLConnection con = (HttpURLConnection) object.openConnection();
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "application/json");
                    con.setRequestMethod("GET");

                    String query = "";
                    OutputStream os = con.getOutputStream();
                    os.write(query.getBytes());
                    os.flush();
                    BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
                    String output;

                    int i=0;
                    while ((output = br.readLine()) != null)
                    {

                        JSONParser jsonParser=new JSONParser();
                        jsonObject = (JSONObject)jsonParser.parse(output);
                        jsonObject = (JSONObject)jsonObject.get("data");

                    }

                    con.disconnect();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public void onbackclicked(ActionEvent actionEvent) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) patient.getScene().getWindow();
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

    public void onsearchclicked(ActionEvent actionEvent) {
        details.setText("Getting Data ...");
        while (jsonObject==null);
        System.out.println("Not NULL");
        JSONArray jsonArray = (JSONArray) jsonObject.get("rawPatientData");
        String id = patient.getText();
        int pno = Integer.valueOf(id);
        pno--;
        JSONObject pdata = (JSONObject)jsonArray.get(pno);
        String para = "";
        para+="reportedOn : " + pdata.get("reportedOn") + "\n";
        para+="city : " + pdata.get("city") + "\n";
        para+="district : " + pdata.get("district") + "\n";
        para+="state : " + pdata.get("state") + "\n";
        para+="status : " + pdata.get("status" ) + "\n";
        para+="notes : " +pdata.get("notes") + "\n";
        para+="Source of virus : " + pdata.get("contractedFrom") + "\n";
        System.out.println(para);
        details.setText(para);
    }
}
