package advanced.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

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

    public void insetZomatoData(Map<String, String> map) {
        Connection connection = createConnection();
        try {
            Statement st = connection.createStatement();
            String insert = "INSERT INTO zomato_table (res_name, res_address, res_lat, res_lon, res_avg_rating) "
                    + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement prep = connection.prepareStatement(insert);
            prep.setString(1, map.get("name"));
            prep.setString(2, map.get("address"));
            prep.setString(3, map.get("latitude"));
            prep.setString(4, map.get("longitude"));
            prep.setString(5, map.get("aggregate_rating"));

            prep.execute();

            String select = "SELECT * FROM zomato_table";
            ResultSet result = st.executeQuery(select);

            while (result.next()) {
                if (result.isLast()) {
                    System.out.println(
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

}
