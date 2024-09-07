package org.example;

import org.example.Interface.IDB;

import java.io.*;
import java.util.ArrayList;

public class DataBase implements IDB {
    private static final String fileName = "DbCoffeeSettings.txt";
    private static DataBase instance;

    private DataBase() {
    }
    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }
    public void saveData(String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(data+"\n");
            System.out.println("Text has been written to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<String> loadData() {
        System.out.println("load is work");
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return list;

    }

    @Override
    public void deleteData() {

    }

    @Override
    public void updateData(String what, String to) {

    }
}