/**
 * This class is used to get weather details from 5day/3hour API
 * 
 */
package weatherforecast;

/**
 *
 * @author kavyareddy
 */

import java.io.*;
import java.net.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FiveDay extends Thread{
    URL url;
    public int listArrayLen;
    public String date[];
    public double temperature[];
    String description[];
    
    
    FiveDay(String urlToRead) {
        System.out.println("Creating fiveday thread");
        try {
            this.url = new URL(urlToRead);
        }catch(Exception e) {
            e.printStackTrace();
        }        
    }
    
    WeatherInfo  info = new WeatherInfo();
    
    public void run() {
      System.out.println("Running 5 Thread");
      StringBuilder result = new StringBuilder();      
      
      try {
        //Retrieves the data from API
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
        String line;
        double temp=0.0;
        while ((line = rd.readLine()) != null) {
          result.append(line);
        }
        //Converting the String objects to JSON objects
        JSONParser parser = new JSONParser();
        JSONObject jobj = (JSONObject)parser.parse(result.toString());
        JSONArray listArray = (JSONArray) jobj.get("list");
        
        listArrayLen=listArray.size();
        
        date = new String[listArrayLen];
        temperature = new double[listArrayLen];
        description = new String[listArrayLen];
        
        for(int i=0;i<listArray.size();i++)
        {
          JSONObject jsonobj_1 = (JSONObject)listArray.get(i);
          
          date[i]=jsonobj_1.get("dt_txt").toString();
          
          //Retrieving level 2 values from JSON data
          JSONArray weatherArray = (JSONArray)jsonobj_1.get("weather");
          JSONObject jobj3 = (JSONObject)weatherArray.get(0);
          description[i] = jobj3.get("main").toString();
          
          try{  
          JSONObject jobj2 = (JSONObject)jsonobj_1.get("main");
          //Converting kelvin to fahrenheit
          temp=Math.round((1.8 * (((Double)jobj2.get("temp")) - 273)) + 32);
          temperature[i]=temp;
          }catch(Exception e) {
              e.printStackTrace();
          }          
        }
      rd.close();
      } catch(Exception e) {
          e.printStackTrace();
      }     
    }
}
