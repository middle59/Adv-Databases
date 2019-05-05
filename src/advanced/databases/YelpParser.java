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
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author MikeM
 */
public class YelpParser {
    
    private final MySqlConnection sql_connection = new MySqlConnection();

    public YelpParser(){}

    public void parseAndStore(String json) 
    {
        try 
        {
            //System.out.println("YELP RETURNED: "+json.toString());
            
            /*
            "id": "39qmzoLvI65BhqAplZi29Q", 
            "alias": "manila-cafe-and-asian-mart-mount-laurel", 
            "name": "Manila Cafe & Asian Mart", 
            "image_url": "https://s3-media2.fl.yelpcdn.com/bphoto/Y2iGEbQAJqADKRssMXAReQ/o.jpg", 
            "is_closed": false, 
            "url": "https://www.yelp.com/biz/manila-cafe-and-asian-mart-mount-laurel?adjust_creative=5lcvWn1fEGpKIpIkWHqOLg&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=5lcvWn1fEGpKIpIkWHqOLg", 
            "review_count": 61, 
            "categories": [{"alias": "filipino", "title": "Filipino"}, {"alias": "importedfood", "title": "Imported Food"}], 
            "rating": 4.5, 
            "coordinates": {"latitude": 39.9527563, "longitude": -74.8707239}, 
            "transactions": [], 
            "price": "$", 
            "location": {"address1": "200 Larchmont Blvd", 
                "address2": "Ste 10", 
                "address3": "", 
                "city": "Mount Laurel", 
                "zip_code": "08054", 
                "country": "US", 
                "state": "NJ", 
                "display_address": ["200 Larchmont Blvd", "Ste 10", "Mount Laurel, NJ 08054"]}, 
                "phone": "+18562220604", 
                "display_phone": "(856) 222-0604", 
                "distance": 497.43274075656007}
            */
            
            Object obj = new JSONParser().parse(json);

            JSONObject jo = (JSONObject) obj;
            JSONArray ja = (JSONArray) jo.get("businesses");

            for (int i = 0; i < ja.size(); i++) 
            {
                JSONObject o = (JSONObject) ja.get(i);
                
                BusinessInfo business_info = new BusinessInfo();
                business_info.name = (String) o.get("name");
                Map loc_address =  (HashMap) o.get("location");
                business_info.full_address = String.valueOf(loc_address.get("address1")) + " " +
                        String.valueOf(loc_address.get("city")) + ", " +
                        String.valueOf(loc_address.get("state")) + " " +
                        String.valueOf(loc_address.get("zip_code"));
                Map loc_coords =  (HashMap) o.get("coordinates");
                business_info.latitude = (Double) loc_coords.get("latitude");
                business_info.longitude = (Double) loc_coords.get("longitude");
                business_info.phone_num = (String) o.get("display_phone");
                business_info.rating = (Double) o.get("rating");
                
                System.out.println(business_info.toString());
                
                sql_connection.insertBusinessInfo(business_info);
            }

        } 
        catch (ParseException ex) 
        {
            Logger.getLogger(ZomatoParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
