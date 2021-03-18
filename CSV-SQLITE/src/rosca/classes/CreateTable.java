package rosca.classes;

//region Imports
import rosca.classes.CreateDatabaseAndConnection;
import java.sql.SQLException;
import java.sql.Statement;
//endregion

public class CreateTable extends CreateDatabaseAndConnection {

    //Constructor
    public CreateTable(String databaseName) throws  SQLException {
        super(databaseName);

        //Will be used for showing result to console
        int result = 0;

        //Will work with this statement
        Statement statement = super.getConnection().createStatement();

        //The table creation
        String createTable =    "CREATE TABLE IF NOT EXISTS database_table \n(\n" +
                "\tA VARCHAR(20),\n" +
                "\tB VARCHAR(20),\n" +
                "\tC VARCHAR(50),\n" +
                "\tD VARCHAR(6),\n" +
                "\tE VARCHAR(2000),\n" +
                "\tF VARCHAR(50),\n" +
                "\tG VARCHAR(10),\n" +
                "\tH VARCHAR(5),\n" + //SQLite does not have BOOL type
                "\tI VARCHAR(5),\n" +
                "\tJ VARCHAR(35)\n)\n";

        //Code of operation, 1 if complete, 0 if table exists, -1 if error
        result = statement.executeUpdate(createTable);

        //Closing the statement
        statement.close();

        //Setting auto commit false
        super.getConnection().setAutoCommit(false);

        //Commiting the query to database
        super.getConnection().commit();

        //Showing the result
        System.out.println("Table created, result: " + result);
        System.out.println("Query: \n\n" + createTable);
    }
}
