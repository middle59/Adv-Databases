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

/**
 *
 * @author nick mariani
 */
public class ZomatoParser 
{
    private final Map<String, String> resultMap;
    private final MySqlConnection sql_connection = new MySqlConnection();

    public ZomatoParser() 
    {
        resultMap = new HashMap<>();
    }

    public void parseAndStore(String json) 
    {
        try 
        {
            Object obj = new JSONParser().parse(json);

            JSONObject jo = (JSONObject) obj;
            JSONArray ja = (JSONArray) jo.get("nearby_restaurants");

            for (int i = 0; i < ja.size(); i++) 
            {
                JSONObject o = (JSONObject) ja.get(i);
                Map infoMap = (HashMap) o.get("restaurant");
                Map locMap = (HashMap) infoMap.get("location");
                Map ratingMap = (HashMap) infoMap.get("user_rating");

                resultMap.put("name", String.valueOf(infoMap.get("name")));
                resultMap.put("address", String.valueOf(locMap.get("address")).replace(",", ""));
                resultMap.put("latitude", String.valueOf(locMap.get("latitude")));
                resultMap.put("longitude", String.valueOf(locMap.get("longitude")));
                resultMap.put("aggregate_rating", String.valueOf(ratingMap.get("aggregate_rating")));

                sql_connection.insetDataIntoTable(resultMap, "zomato_table");
            }

        } 
        catch (ParseException ex) 
        {
            Logger.getLogger(ZomatoParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
