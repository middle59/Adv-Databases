package advanced.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/** FOR INSTALLATION: MySQLInstaller for Windows
 *  SQL Connector/J for 8.0.15
 *  Add jar to project (Project Properties - Libraries - Run)
 *  Verify connection info below
 */
public class MySqlConnection {
    
    private final String DB_NAME="jdbc:mysql://localhost:3306";
    private final String DB_USER="root";
    private final String DB_DEFINITELY_NOT_THE_PASS="root";
    private final String USE_DB_NAME="ap";
    
    public MySqlConnection()
    {
        
    }
    
    private Connection createConnection()
    {
        Connection connection = null;
        
        try
        {
          // create a mysql database connection
          Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
          connection = DriverManager.getConnection(DB_NAME, DB_USER, DB_DEFINITELY_NOT_THE_PASS);
          connection.setCatalog(USE_DB_NAME);
        }
        catch (Exception e)
        {
          System.err.println("MySqlConnection.createConnection got an exception!");
          System.err.println(e.getMessage());
        }
        
        return connection;
    }
    
    public void tryQuery()
    {
        Connection connection = createConnection();
        try
        {
          Statement st = connection.createStatement();
          //String query = "INSERT INTO general_ledger_accounts VALUES (444, 'TestConnection')";
          String query = "SELECT * FROM general_ledger_accounts";
          ResultSet result=st.executeQuery(query); 
          while (result.next()) {
            System.out.println(result.getInt("account_number") +","+result.getString("account_description"));
        }
          connection.close();
        }
        catch (Exception e)
        {
          System.err.println("MySqlConnection.createConnection got an exception!");
          System.err.println(e.getMessage());
        }
    }
    
}
