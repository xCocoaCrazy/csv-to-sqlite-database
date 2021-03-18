# CSV -> SQLite

This program is made to parse the data from a .csv file, and introduce it to a SQLite database. 
The rows which are not legit(they don't have 1 or more rows), the are written to a .csv with the name bad-<timestamp>.csv. Also at the end a log-<timestamp>.txt file is created, where is written how many rows were written, how many were successful, and how many were bad. 

The code will create a test.db, which is the datbase. To change the name of the databse, just rename test from main() `ReadFromCSV read = new ReadFromCSV("test");` to the name that you want you database to be.

To run the program, I've tried to do a .jar, but that doesn't work, so for now the only way to run the code is from Intellij IDEA or Eclipse or other program that runs Java code, and run the main() from Main class.
