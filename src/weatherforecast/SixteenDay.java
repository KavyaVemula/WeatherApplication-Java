/**
 * This class is used to get weather details from 7day/daily API
 * 
 */
package weatherforecast;

/**
 *
 * @author kavyareddy
 */

import java.io.*;
import java.net.*;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
public class SixteenDay extends Thread{
    URL url;
    double min[];
    double max[];
    public int listArrayLen;
    String date[];
    
    SixteenDay(String urlToRead) {
        System.out.println("Creating Sixteen Thread");
        try {
            this.url = new URL(urlToRead);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void run() {
        System.out.println("Running 16 Thread");
        try {
            StringBuilder result = new StringBuilder();
  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));      
            String line;
            double min_temp=0.0;
            double max_temp=0.0;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            JSONParser parser = new JSONParser();
            JSONObject jobj = (JSONObject)parser.parse(result.toString());
            JSONArray listArray = (JSONArray) jobj.get("list");
            listArrayLen = listArray.size();
            min = new double[listArrayLen];
            max = new double[listArrayLen];
            date = new String[listArrayLen];
            for(int i=0;i<listArray.size();i++)
            {
                JSONObject jsonobj_1 = (JSONObject)listArray.get(i);
                JSONArray weatherArray = (JSONArray)jsonobj_1.get("weather");
                JSONObject jobj3 = (JSONObject)weatherArray.get(0);
                
                try{  
                JSONObject jobj2 = (JSONObject)jsonobj_1.get("temp");
                min_temp=Math.round((1.8 * (((Double)jobj2.get("min")) - 273)) + 32);
                max_temp=Math.round((1.8 * (((Double)jobj2.get("max")) - 273)) + 32);
                Date d = new Date((Long.parseLong(String.valueOf(jsonobj_1.get("dt"))))*1000);
                date[i] = (d.toString());
                
                min[i] = min_temp;
                max[i] = max_temp;                
                }catch(Exception e) {
                    e.printStackTrace();
                }                
            }
            rd.close();            
        }catch (Exception e) {
                  e.printStackTrace();
        }
    }
}
