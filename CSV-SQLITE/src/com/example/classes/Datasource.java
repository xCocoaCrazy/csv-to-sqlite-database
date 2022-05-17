package com.example.classes;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Datasource {
    private final String DB_NAME;
    private final String TABLE_NAME;
    private final String CONNECTION_STRING;

    public Datasource(String Database_Name, String table_name, String fileLocation) {
        this.DB_NAME = Database_Name;
        TABLE_NAME = table_name;
        CONNECTION_STRING = "jdbc:sqlite:./" + DB_NAME + ".db";
        createTable(fileLocation);
        readFromCSV(fileLocation);
    }

    private void createTable(String fileName) {
        try (Scanner reader = new Scanner(new File(fileName));
             Connection connection = DriverManager.getConnection(CONNECTION_STRING);
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS " + TABLE_NAME);
            String lineText = reader.nextLine();
            ArrayList<String> col = new ArrayList<>(List.of(lineText.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1)));
            StringBuilder stringBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append("( ");

            col.removeIf(e -> e.equals(""));

            for(int i = 0; i < col.size(); i++) {
                if(i == col.size() - 1) {
                    stringBuilder.append(col.get(i)).append(" TEXT)");
                } else {
                    stringBuilder.append(col.get(i)).append(" TEXT,");
                }
            }
            System.out.println(stringBuilder);
            statement.execute(stringBuilder.toString());
        } catch (SQLException e) {
            System.out.println("Could not create connection with the database: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file " + fileName + ": " + e.getMessage());
        }
    }

    private void readFromCSV(String fileName) {
        int goodInserts = 0; //Will be used to check the number of good inserts
        int badInserts = 0; //Will be used to check the number of bad inserts
        int numberInserts = 0; //Will be used to check the total number of inserts
        File badFile = new File(new StringBuilder() //The file where will be written the bad file
                .append("bad-data-" + DB_NAME + "-")
                .append((new SimpleDateFormat("yyyyMMddHHmm").format(new Date())))
                .append(".csv").toString());
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(badFile));
            Scanner reader = new Scanner(new File(fileName));
            Connection connection = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = connection.createStatement()) {
            reader.nextLine(); //Skips header line

            while(reader.hasNext()) {
                String lineText = reader.nextLine();

                //TODO find a way to replace this with something better and more effective
                String[] data = lineText.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                //Reading the strings for data
                ArrayList<String> inserts = new ArrayList<>();
                inserts.add(data[0]);
                inserts.add(data[1]);
                inserts.add(data[2]);
                inserts.add(data[3]);
                inserts.add(data[4]);
                inserts.add(data[5]);
                inserts.add(data[6]);
                inserts.add(data[7]);
                inserts.add(data[8]);
                inserts.add(data[9]);

                numberInserts++;

                if(inserts.stream().noneMatch(e -> e.equals(""))) {
                    StringBuilder sb = new StringBuilder("INSERT INTO ").append(TABLE_NAME).append("(");
                    //Retrieving the data
                    ResultSet rs = statement.executeQuery("select * from " + TABLE_NAME);
                    //Retrieving the ResultSetMetadata object
                    ResultSetMetaData rsMetaData = rs.getMetaData();
                    //Retrieving the list of column names
                    for(int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                        if(i == rsMetaData.getColumnCount()) {
                            sb.append(rsMetaData.getColumnName(i) + ") VALUES (");
                        } else {
                            sb.append(rsMetaData.getColumnName(i) + ", ");
                        }
                    }

                    for(int i = 0; i < inserts.size(); i++) {
                        if(i == inserts.size() - 1) {
                            sb.append("\'").append(inserts.get(i)).append("\')");
                        } else {
                            sb.append("\'").append(inserts.get(i)).append("\', ");
                        }
                    }
                    goodInserts++;
                    statement.execute(sb.toString());
                } else {
                    StringBuilder sb = new StringBuilder();
                    for(int i = 0; i < inserts.size(); i++) {
                        if(i == inserts.size() - 1) {
                            sb.append(inserts.get(i)).append(",,,,,");
                        } else {
                            sb.append(inserts.get(i)).append(", ");
                        }
                    }
                    badInserts++;
                    writer.write(sb.toString());
                    writer.newLine();
                }

                File logFile = new File(new StringBuilder()
                        .append("logs-" + DB_NAME + "-").append(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()))
                        .append(".txt").toString());

                BufferedWriter writerLog = new BufferedWriter(new FileWriter(logFile));
                writerLog.write(numberInserts + " records received");
                writerLog.newLine();
                writerLog.write(goodInserts + " records successful");
                writerLog.newLine();
                writerLog.write(badInserts + " records failed");
                writerLog.newLine();

                writerLog.close();
            }
        } catch (IOException e) {
            System.out.println("The looging file for bad recordings was not created: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("The connection with the database could not be established: " + e.getMessage());
        }
    }



}
