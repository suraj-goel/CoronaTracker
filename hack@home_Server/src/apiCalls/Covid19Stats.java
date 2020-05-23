package apiCalls;



import data.CovidGeneral;
import mainClasses.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import request.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class Covid19Stats implements Serializable {

    private CovidGeneral covidGeneral;
    public Covid19Stats(){

        covidGeneral=new CovidGeneral();
        try
        {

            String url="https://api.rootnet.in/covid19-in/stats/latest";
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
                JSONObject jsonObject = (JSONObject)jsonParser.parse(output);
                JSONObject jsonObjectdata= (JSONObject) jsonObject.get("data");
                JSONObject jsonObjectSummary = (JSONObject)jsonObjectdata.get("summary");
                covidGeneral.setTotalCases((long)jsonObjectSummary.get("total"));
                covidGeneral.setConfirmedIndians((long)jsonObjectSummary.get("confirmedCasesIndian"));
                covidGeneral.setForeigners((long)jsonObjectSummary.get("confirmedCasesForeign"));
                covidGeneral.setDischarged((long)jsonObjectSummary.get("discharged"));
                covidGeneral.setDeaths((long)jsonObjectSummary.get("deaths"));

                JSONArray regions = (JSONArray)jsonObjectdata.get("regional");
                for(int j=0;j<regions.size();j++){
                    JSONObject region = (JSONObject)regions.get(j);
                    String loc = (String)region.get("loc");
                    long cases = (long)region.get("totalConfirmed");
//                    System.out.println(loc);
//                    System.out.println(cases);
                    covidGeneral.mp.put(loc.toLowerCase(),cases);
                }

            }

            con.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public Response getResponse(){
        if(covidGeneral!=null){
            return new Response(Main.generateUid(),covidGeneral,"success");
        }
        return new Response(Main.generateUid(),null,"failed");
    }
}
