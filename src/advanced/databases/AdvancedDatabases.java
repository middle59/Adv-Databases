package advanced.databases;

public class AdvancedDatabases {

    private final static String yelpApi = "Bearer 1hOgzONYZ4QP4IW9FC75gYg8ULVh7k4fMfstvZcUhA8MgenNSQWEXMGPG8HMwy83eSYRx1lmCEmjoz3c5o8a13bN5_XmkgerXutq_LLpFrxChUnQyxE1byyPLmqqXHYx";
    private final static String zomatoApi = "d981f745d319d6466da8b2761a2e49cc";
    private final static String googleApi = "AIzaSyDIuQVwm1uyIhylp4vFsAthUkjw6iYN0Hw";
    
    private final static String yelpUrl = "https://api.yelp.com/v3/businesses/search?text=del&latitude=@LAT@&longitude=@LON@";
    private final static String zomatoUrl = "https://developers.zomato.com/api/v2.1/geocode?lat=@LAT@&lon=@LON@";
    private final static String googleUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=@LAT@,@LON@&radius=1500&type=restaurant&key="+googleApi;

    private final static String lat_replace = "@LAT@", lon_replace = "@LON@";
    
    private final static double  default_lat = 39.950088, defualt_lon = -74.866040; 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GeneralHttpURLConnection http_connection = new GeneralHttpURLConnection();

        System.out.println("============YELP HTTP CONNECTION============");
        yelpHTTPGet(http_connection, default_lat, defualt_lon);

        System.out.println("============ZOMATO HTTP CONNECTION============");
        zomatoHTTPGet(http_connection, default_lat, defualt_lon);
        
        System.out.println("============GOOGLE HTTP CONNECTION============");
        googleHTTPGet(http_connection, default_lat, defualt_lon);
        
        MySqlConnection sql_connection = new MySqlConnection();
        sql_connection.averageBusinessRatings();
    }
    
    public static void yelpHTTPGet(GeneralHttpURLConnection http_connection, double lat, double lon)
    {
        String yelp_final_url = (yelpUrl.replaceAll(lat_replace, ""+lat)).replaceAll(lon_replace, ""+lon);
        System.out.println("final yelp url: " + yelp_final_url);
        http_connection.sendGet(yelp_final_url, "Authorization", yelpApi, "yelp");
    }
    
    public static void zomatoHTTPGet(GeneralHttpURLConnection http_connection, double lat, double lon)
    {
        String zomato_final_url = (zomatoUrl.replaceAll(lat_replace, ""+lat)).replaceAll(lon_replace, ""+lon);
        System.out.println("final zomato url: " + zomato_final_url);
        http_connection.sendGet(zomato_final_url, "user-key", zomatoApi, "zomato");
    }
    
    public static void googleHTTPGet(GeneralHttpURLConnection http_connection, double lat, double lon)
    {
        String google_final_url = (googleUrl.replaceAll(lat_replace, ""+lat)).replaceAll(lon_replace, ""+lon);
        System.out.println("final yelp url: " + google_final_url);
        http_connection.sendGet(google_final_url, "key", googleApi, "google");
    }

}
