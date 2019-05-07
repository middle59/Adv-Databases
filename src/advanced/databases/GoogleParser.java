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
                BusinessInfo business_info = new BusinessInfo();
                JSONObject o = (JSONObject) ja.get(i);
                Map geoMap = (HashMap) o.get("geometry");
                Map locMap = (HashMap) geoMap.get("location");
                
                business_info.name = (String) o.get("name");
                business_info.full_address = (String) o.get("vicinity");
                business_info.latitude = (Double) locMap.get("lat");
                business_info.longitude = (Double) locMap.get("lng");
                business_info.rating = (Double) o.get("rating");

                System.out.println(business_info.toString());
                
                sql_connection.insertBusinessInfo(business_info, "google_table");
            }

        } 
        catch (ParseException ex) 
        {
            Logger.getLogger(GoogleParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
