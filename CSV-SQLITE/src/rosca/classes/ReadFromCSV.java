package rosca.classes;

//region Imports
import rosca.classes.CreateTable;
import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
//endregion

public class ReadFromCSV extends CreateTable {

    //Constructor
    public ReadFromCSV (String databaseName) throws SQLException, IOException {
        super(databaseName);

        //region Creating variables
        int goodInserts = 0; //Will be used to check the number of good inserts
        int badInserts = 0; //Will be used to check the number of bad inserts
        int numberInserts = 0; //Will be used to check the total number of inserts
        File badFile = new File(new StringBuilder() //The file where will be written the bad file
                .append("bad-data-")
                .append((new SimpleDateFormat("yyyyMMddHHmm").format(new Date())))
                .append(".csv").toString());

        if(badFile.createNewFile()) { //If the file is created
            System.out.println("File created: " + badFile.getName());
        } else { //If the file exists
            System.out.println("File " + badFile.getName() + " already exists!");
        }

        //Showing to console the user to wait
        System.out.println("\nWriting to database and to " + badFile.getName() + "...\nPlease wait");

        //Write to file
        BufferedWriter writer = new BufferedWriter(new FileWriter(badFile));

        //Creating the statement
        Statement statement = super.getConnection().createStatement();

        //Declaring the file from which we will read
        File myFile = new File("data.csv");

        //Declaring the reader
        Scanner read = new Scanner(myFile);
        String lineText = null;
        //endregion

        read.nextLine(); //Skip header

        //Reading from csv and insert to database
        while(read.hasNext()) {
            //Reading the whole line
            lineText = read.nextLine();

            //Will split the data from ","
            String[] data = lineText.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

            //Reading the strings for data
            String A = data[0];
            String B = data[1];
            String C = data[2];
            String D = data[3];
            String E = data[4];
            String F = data[5];
            String G = data[6];
            String H = data[7];
            String I = data[8];
            String J = data[9];

            numberInserts++;

            if(!"".equals(A) &&
                    !"".equals(B) &&
                    !"".equals(C) &&
                    !"".equals(D) &&
                    !"".equals(E) &&
                    !"".equals(F) &&
                    !"".equals(G) &&
                    !"".equals(H) &&
                    !"".equals(I) &&
                    !"".equals(J) ) {
                goodInserts++;
                //Execution query
                String sql = new StringBuilder()
                        .append("INSERT INTO database_table(A, B, C, D, E, F, G, H, I, J) VALUES ")
                        .append("(\'")
                        .append(A).append("\', \'")
                        .append(B).append("\', \'")
                        .append(C).append("\', \'")
                        .append(D).append("\', \'")
                        .append(E).append("\', \'")
                        .append(F).append("\', \'")
                        .append(G).append("\', \'")
                        .append(H).append("\', \'")
                        .append(I).append("\', \'")
                        .append(J).append("\')").toString();

                //Executing the update
                statement.executeUpdate(sql);

                //Executing the query
                super.getConnection().commit();
            } else {
                badInserts++;
                String bad = new StringBuilder()
                        .append(A).append(",")
                        .append(B).append(",")
                        .append(C).append(",")
                        .append(D).append(",")
                        .append(E).append(",")
                        .append(F).append(",")
                        .append(G).append(",")
                        .append(H).append(",")
                        .append(I).append(",")
                        .append(J).append(",,,,,").toString();
                writer.write(bad);
                writer.newLine();
            }
        }

        //region Writing to log file
        File logFile = new File(new StringBuilder()
                .append("logs-").append(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()))
                .append(".txt").toString());
        if(logFile.createNewFile()) {
            System.out.println("Log file created: " + logFile.getName());
        } else {
            System.out.println("Log file " + logFile.getName() + " already exists!");
        }

        BufferedWriter writerLog = new BufferedWriter(new FileWriter(logFile));
        writerLog.write(numberInserts + " records received");
        writerLog.newLine();
        writerLog.write(goodInserts + " records successful");
        writerLog.newLine();
        writerLog.write(badInserts + " records failed");
        writerLog.newLine();
        //endregion

        //region Closing variables
        writerLog.close();
        statement.close();
        read.close();
        writer.close();
        super.getConnection().close();
        //endregion

    }
}
