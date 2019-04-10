package advanced.databases;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Zomato {
    
        public Zomato(){}
    
    	// HTTP GET request
	public void sendGet()
        {
            try
            {
                String url = "https://developers.zomato.com/api/v2.1/geocode?lat=39.950088&lon=-74.866040";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");

                String apiKey = "d981f745d319d6466da8b2761a2e49cc";
                con.setRequestProperty("user-key", apiKey);

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
                System.err.println("Zomato.sendGet got an exception!");
                System.err.println(e.getMessage());
            }
	}
}
