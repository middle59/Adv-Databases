/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advanced.databases;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.util.Map;

/**
 *
 * @author Scott-DT
 */

public class GoogleParser {
    private final Map<String, String> resultMap;
    private final MySqlConnection sql_connection = new MySqlConnection(); 
    
    public GoogleParser() 
    {
        resultMap = new HashMap<>();
    }
   
    public void parseAndStore(String json) 
    {
        try 
        {
            Object obj = new JSONParser().parse(json);

            JSONObject jo = (JSONObject) obj;
            JSONArray ja = (JSONArray) jo.get("results");
            for (int i = 0; i < ja.size(); i++) 
            {
                JSONObject o = (JSONObject) ja.get(i);
                Map geoMap = (HashMap) o.get("geometry");
                Map locMap = (HashMap) geoMap.get("location");
                //Map ratingMap = (HashMap) infoMap.get("user_rating");
                
                System.out.println("name: " + o.get("name"));
                System.out.println("address: " + o.get("vicinity"));
                System.out.println("lat: " + locMap.get("lat"));
                System.out.println("lon: " + locMap.get("lng"));
                System.out.println("rating: " + o.get("rating"));

                //resultMap.put("name", String.valueOf(infoMap.get("name")));
                //resultMap.put("address", String.valueOf(locMap.get("address")).replace(",", ""));
                //resultMap.put("latitude", String.valueOf(locMap.get("latitude")));
                //resultMap.put("longitude", String.valueOf(locMap.get("longitude")));
                //resultMap.put("aggregate_rating", String.valueOf(ratingMap.get("aggregate_rating")));

                //sql_connection.insetZomatoData(resultMap);
            }

        } 
        catch (ParseException ex) 
        {
            Logger.getLogger(GoogleParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
