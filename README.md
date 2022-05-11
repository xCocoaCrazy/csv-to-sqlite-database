# CSV -> SQLite

This program is made to parse the data from a .csv file, and introduce it to a SQLite database. 
The rows which are not legit(they don't have 1 or more rows), the are written to a .csv with the name bad-<timestamp>.csv. Also at the end a log-<timestamp>.txt file is created, where is written how many rows were written, how many were successful, and how many were bad. 

The code will create a test.db, which is the datbase. To change the name of the databse, just rename test from main() `ReadFromCSV read = new ReadFromCSV("test");` to the name that you want you database to be.

# How to run? 
If you are an experienced user, you will sure find a way to run this project, otherwise below are some ways to start the project. To start the project run Main() method from Main.java class. 

## For Intellij Idea
For Intellij Idea, there are more ways to open the project. You can start Intellij Idea and choose the option "Get from VCS" and insert the URL for this project. The IDE will do everything for you. 
Be sure to use the proper SDK. You can check the SDK by going to File->Project Structure->Project->Project SDK, and be sure the "openjdk-18 version 18.0.1" is chosen.
Also for Idea you can clone the project to a folder you wish and open the project by opening from Idea pom.xml file.

## For Eclipse IDE
For eclipse I've found only the version to clone the project to a folder you wish and then open the project by Open project from eclipse and choose vendingmachine folder.
Be sure to use the proper SDK, which is "openjdk-18 version 18.0.1".
