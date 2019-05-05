package advanced.databases;

public class AdvancedDatabases {

    private final static String yelpApi = "Bearer 1hOgzONYZ4QP4IW9FC75gYg8ULVh7k4fMfstvZcUhA8MgenNSQWEXMGPG8HMwy83eSYRx1lmCEmjoz3c5o8a13bN5_XmkgerXutq_LLpFrxChUnQyxE1byyPLmqqXHYx";
    private final static String zomatoApi = "d981f745d319d6466da8b2761a2e49cc";
    private final static String googleApi = "AIzaSyDIuQVwm1uyIhylp4vFsAthUkjw6iYN0Hw";
    
    private final static String yelpUrl = "https://api.yelp.com/v3/businesses/search?text=del&latitude=39.950088&longitude=-74.866040";
    private final static String zomatoUrl = "https://developers.zomato.com/api/v2.1/geocode?lat=39.950088&lon=-74.866040";
    private final static String googleUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=39.950088,-74.866040&radius=1500&type=restaurant&key="+googleApi;
//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=YOUR_API_KEY


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GeneralHttpURLConnection http_connection = new GeneralHttpURLConnection();

        System.out.println("============YELP HTTP CONNECTION============");
        //http_connection.sendGet(yelpUrl, "Authorization", yelpApi);

        System.out.println("============ZOMATO HTTP CONNECTION============");
        //http_connection.sendGet(zomatoUrl, "user-key", zomatoApi);
        
        System.out.println("============GOOGLE HTTP CONNECTION============");
        http_connection.sendGet(googleUrl, "key", googleApi);
    }

}
