package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private String URL = "jdbc:postgresql://localhost:5433/users_db";
    private String USER = "postgres";
    private String PASSWORD = "1";

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class){
                if (instance == null){
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }
}
