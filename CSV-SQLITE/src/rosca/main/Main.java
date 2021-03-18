package rosca.main;


import rosca.classes.ReadFromCSV;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            ReadFromCSV read = new ReadFromCSV("test");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("Oops, file not found!");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Oops, problems with file!");
            e.printStackTrace();
        }

    }
}
