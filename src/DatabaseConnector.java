import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:postgresql://localhost:5432/fitness_tracker";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Password1234";

    public static Connection getConnection() throws SQLException {
        try {
            System.out.println(" Loading PostgreSQL driver...");
            Class.forName("org.postgresql.Driver");
            System.out.println(" Driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println(" Driver not found: " + e.getMessage());
            throw new SQLException("Driver not found!", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            System.out.println(" Database connection successful!");

            // Check if there are tables in the database
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            System.out.println(" Checking tables in the database:");
            boolean hasTables = false;
            while (tables.next()) {
                System.out.println(" Found table: " + tables.getString("TABLE_NAME"));
                hasTables = true;
            }
            if (!hasTables) {
                System.out.println(" No tables found in the database.");
            }
        } catch (SQLException e) {
            System.out.println(" Connection error: " + e.getMessage());
        }
    }
}
