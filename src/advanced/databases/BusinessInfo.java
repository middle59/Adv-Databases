/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advanced.databases;

/**
 *
 * @author MikeM
 */
public class BusinessInfo {
    
    public String name = "";
    public String full_address = "";
    public Double latitude = 0.0;
    public Double longitude = 0.0;
    public String phone_num = "";
    public Double rating = 0.0; 
    
    public BusinessInfo() {}
    
    public String toString()
    {
        String output = "---- Location Name: " + name + " ----" + '\n' +
            "Address: " + full_address + '\n' +
            "Latitude: " + latitude + '\n' +
            "Longitude: " + longitude + '\n' +
            "Phone Number: " + phone_num + '\n' +
            "Rating: " + rating + '\n';
        return output;
    }
    
}
