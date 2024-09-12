package org.example;

import org.example.Inteface.IDB;

import java.io.*;
import java.util.ArrayList;

public class DataBase implements IDB {

    private static DataBase dataBase;
    private static final String fileName = "databaseGameInf.txt";

    private DataBase (){
    }

    public static DataBase getInstance(){
        if (dataBase == null){
            dataBase = new DataBase();
        }
        return dataBase;
    }


    public void save(String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(data+"\n");
            System.out.println("Text has been written to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<String> loadData() {

        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return list;

    }

    @Override
    public void update(String data) {

    }

    @Override
    public void delete(String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            writer.write("");
            System.out.println("Text has been cleared from " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
