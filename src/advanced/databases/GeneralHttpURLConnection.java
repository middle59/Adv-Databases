package advanced.databases;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeneralHttpURLConnection {
    
        public GeneralHttpURLConnection(){}
    
    	// HTTP GET request
	public void sendGet()
        {
            try
            {
		//String url = "https://api.yelp.com/v3/businesses/search";
                String url = "https://api.yelp.com/v3/autocomplete?text=del&latitude=37.786882&longitude=-122.399972";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");

		//add request header
		//con.setRequestProperty("location", "New York City");
                String apiKey = "Bearer 1hOgzONYZ4QP4IW9FC75gYg8ULVh7k4fMfstvZcUhA8MgenNSQWEXMGPG8HMwy83eSYRx1lmCEmjoz3c5o8a13bN5_XmkgerXutq_LLpFrxChUnQyxE1byyPLmqqXHYx";
                con.setRequestProperty("Authorization", apiKey);

		int responseCode = con.getResponseCode();
		System.out.println("Sending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
            }
            catch (Exception e)
            {
                System.err.println("GeneralHttpURLConnection.sendGet got an exception!");
                System.err.println(e.getMessage());
            }
	}
}
