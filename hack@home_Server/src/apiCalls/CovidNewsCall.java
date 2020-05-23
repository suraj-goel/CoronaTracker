package apiCalls;

import data.CovidNews;
import mainClasses.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import request.CovidNewsRequest;
import request.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CovidNewsCall {
    private CovidNews covidNews;

    public CovidNewsCall(CovidNewsRequest covidNewsRequest) {
        String country = covidNewsRequest.getCountry();
        String source = covidNewsRequest.getSource();
        covidNews = new CovidNews();
        try
        {

            String url="http://newsapi.org/v2/top-headlines?" +
                    "country=" + country+"&q=coronavirus"+
                    "&apiKey=fd0791cc07c0499a96f019d11ac43b9c";
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


                JSONArray articles = (JSONArray)jsonObject.get("articles");
                for(int j=0;j<articles.size();j++){
                    JSONObject article = (JSONObject)articles.get(j);
                    String title = (String)article.get("title");
                    String body = (String)article.get("description");
                    covidNews.title.add(title);
                    covidNews.body.add(body);
                    System.out.println(title);

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
        if(covidNews!=null){
            return new Response(Main.generateUid(),covidNews,"success");
        }
        return new Response(Main.generateUid(),null,"failed");
    }
}
