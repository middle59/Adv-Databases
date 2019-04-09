package advanced.databases;

public class AdvancedDatabases {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("============DB CONNECTION============");
        MySqlConnection sql_connection = new MySqlConnection();
        sql_connection.tryQuery();
        
        System.out.println("============HTTP CONNECTION============");
        GeneralHttpURLConnection http_connection = new GeneralHttpURLConnection();
        http_connection.sendGet();
    }
    
}
