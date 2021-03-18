package rosca.classes;

//Imports
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDatabaseAndConnection {
    //Fileds
    private String databaseName;
    private Connection connection;

    //Constructor
    public CreateDatabaseAndConnection(String databaseName) {
        this.databaseName = databaseName;

        //The url
        String url = "jdbc:sqlite:" + this.databaseName + ".db";
        try {
            //Creating the connection
            connection = DriverManager.getConnection(url);

            //Showing info to console
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("The database " + this.databaseName + " was created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Getter
    public Connection getConnection() {
        return connection;
    }
}
