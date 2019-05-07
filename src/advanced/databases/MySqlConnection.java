package advanced.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.HashMap;

/**
 * FOR INSTALLATION: MySQLInstaller for Windows SQL Connector/J for 8.0.15 Add
 * jar to project (Project Properties - Libraries - Run) Verify connection info
 * below
 */
public class MySqlConnection {

    private final String DB_NAME = "jdbc:mysql://localhost:3306";
    private final String DB_USER = "root";
    private final String DB_DEFINITELY_NOT_THE_PASS = "root";
    private final String USE_DB_NAME = "project";

    public MySqlConnection() {

    }

    private Connection createConnection() {
        Connection connection = null;

        try {
            // create a mysql database connection
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(DB_NAME, DB_USER, DB_DEFINITELY_NOT_THE_PASS);
            connection.setCatalog(USE_DB_NAME);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            System.err.println("MySqlConnection.createConnection got an exception!");
            System.err.println(e.getMessage());
        }

        return connection;
    }
    
    public void insertBusinessInfo(BusinessInfo business_info, String table)
    {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("name", business_info.name);
        resultMap.put("address", business_info.full_address);
        resultMap.put("latitude", business_info.latitude+"");
        resultMap.put("longitude", business_info.longitude+"");
        resultMap.put("aggregate_rating", business_info.rating+"");

        insetDataIntoTable(resultMap, table);
    }

    public void insetDataIntoTable(Map<String, String> map, String table) {
        Connection connection = createConnection();
        try {
            Statement st = connection.createStatement();
            String insert = "REPLACE INTO "+ table +" (res_name, res_address, res_lat, res_lon, res_avg_rating) "
                    + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement prep = connection.prepareStatement(insert);
            prep.setString(1, map.get("name"));
            prep.setString(2, map.get("address"));
            prep.setString(3, map.get("latitude"));
            prep.setString(4, map.get("longitude"));
            prep.setString(5, map.get("aggregate_rating"));

            prep.execute();

            String select = "SELECT * FROM "+ table;
            ResultSet result = st.executeQuery(select);

            while (result.next()) {
                if (result.isLast()) {
                    System.out.println("SQL inset into "+ table +": " +
                            result.getInt("res_id") + ","
                            + result.getString("res_name") + ","
                            + result.getString("res_address") + ","
                            + result.getDouble("res_lat") + ","
                            + result.getDouble("res_lon") + ","
                            + result.getFloat("res_avg_rating")
                    );
                }
            }
            connection.close();
        } catch (Exception e) {
            System.err.println("MySqlConnection.createConnection got an exception!");
            System.err.println(e.getMessage());
        }
    }
    
    public void averageBusinessRatings() {
        
        Connection connection = createConnection();
        try {
            
            Statement st = connection.createStatement();
            
            String union_ratings = "SELECT yelp_table.res_name, google_table.res_name, zomato_table.res_name, yelp_table.res_avg_rating, google_table.res_avg_rating, zomato_table.res_avg_rating\n" + 
                "FROM google_table\n" +
                "LEFT JOIN yelp_table ON google_table.res_name = yelp_table.res_name\n" +
                "LEFT JOIN zomato_table ON yelp_table.res_name = zomato_table.res_name\n" +
                "UNION\n" +
                "SELECT yelp_table.res_name, google_table.res_name, zomato_table.res_name, yelp_table.res_avg_rating, google_table.res_avg_rating, zomato_table.res_avg_rating \n" +
                "FROM google_table\n" +
                "RIGHT JOIN yelp_table ON google_table.res_name = yelp_table.res_name\n" +
                "LEFT JOIN zomato_table ON yelp_table.res_name = zomato_table.res_name\n" +
                "UNION\n" +
                "SELECT yelp_table.res_name, google_table.res_name, zomato_table.res_name, yelp_table.res_avg_rating, google_table.res_avg_rating, zomato_table.res_avg_rating \n" +
                "FROM google_table\n" +
                "RIGHT JOIN yelp_table ON google_table.res_name = yelp_table.res_name\n" +
                "RIGHT JOIN zomato_table ON yelp_table.res_name = zomato_table.res_name;";

            ResultSet result = st.executeQuery(union_ratings);
            ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            
            if (columnsNumber % 2 == 1) // uneven # columns for name/rating 
            {
                System.err.println("averageBusinessRatings queried the DB and got an uneven number of name rating pairs! Cannot calculate averages.");
                return;
            }
            
            while (result.next()) {
                String result_str = "Entry : ";
                String business_name = "";
                Double rating_total = 0.0;
                int count = 0;
                
                for (int i = 1; i <= columnsNumber; i++) {
                    result_str += result.getString(i);
                    
                    if(i <= (columnsNumber / 2) && result.getString(i) != null)
                    {
                        business_name = result.getString(i);
                    }
                    else if(i > (columnsNumber / 2) && result.getString(i) != null)
                    {
                        rating_total += Double.parseDouble(result.getString(i));
                        count++;
                    }
                    if(i < columnsNumber)
                         result_str += ", ";
                }
                
                Double avg_rating = rating_total / count;
                System.out.println(result_str);
                System.out.println("Business: "+business_name+", Average Rating: "+avg_rating);
            }
            connection.close();
        } catch (Exception e) {
            System.err.println("MySqlConnection.createConnection got an exception!");
            System.err.println(e.getMessage());
        }
    }

}
