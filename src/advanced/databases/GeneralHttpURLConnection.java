package advanced.databases;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeneralHttpURLConnection {

    public GeneralHttpURLConnection() {
    }

    // HTTP GET request
    public void sendGet(String url, String key, String value) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty(key, value);

            int responseCode = con.getResponseCode();
            System.out.println("Sending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            if (response.toString().contains("zomato")) 
            {
                ZomatoParser parser = new ZomatoParser();
                parser.parseAndStore(response.toString());
            }

        } catch (Exception e) {
            System.err.println("GeneralHttpURLConnection.sendGet got an exception!");
            System.err.println(e.getMessage());
        }
    }
}
