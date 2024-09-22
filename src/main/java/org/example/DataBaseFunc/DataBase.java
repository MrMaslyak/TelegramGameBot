package org.example.DataBaseFunc;

import org.example.Inteface.IDB;

import java.sql.*;
import java.util.ArrayList;

public class DataBase implements IDB {

    private static DataBase dataBase;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "LaLa27418182";

    private DataBase() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to the PostgreSQL server successfully.");

            getAllUsersFromFirstTable(connection);
            getAllUsersFromSecondTable(connection);
            getGameStats(connection);

            connection.close();

        } catch (Exception e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }

    public static DataBase getInstance() {
        if (dataBase == null) {
            dataBase = new DataBase();
        }
        return dataBase;
    }


    private void getAllUsersFromFirstTable(Connection connection) {
        String query = "SELECT * FROM user_first";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Data from user_first:");


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String ownerName = resultSet.getString("name");

                System.out.println("ID: " + id + ", Owner: " + ownerName);
            }

        } catch (SQLException e) {
            System.out.println("An error occurred while retrieving data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void getAllUsersFromSecondTable(Connection connection) {
        String query = "SELECT * FROM user_second";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Data from user_second:");

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String ownerName = resultSet.getString("name");

                System.out.println("ID: " + id + ", Owner: " + ownerName);
            }

        } catch (SQLException e) {
            System.out.println("An error occurred while retrieving data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void getGameStats(Connection connection) {
        String query = "SELECT * FROM game";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Game Stats:");

            while (resultSet.next()) {
                String winName = resultSet.getString("win");
                String loseName = resultSet.getString("lose");

                System.out.println("Win_Name: " + winName + ", Lose_Name: " + loseName);
            }

        } catch (SQLException e) {
            System.out.println("An error occurred while retrieving data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public void save_game_user_stats(String user_name_winner, String user_name_loser) {

        String insertSQL = "INSERT INTO game (win, lose) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, user_name_winner);
            pstmt.setString(2, user_name_loser);

            pstmt.executeUpdate();
            System.out.println("Game result saved successfully!");

        } catch (SQLException e) {
            System.out.println("An error occurred while saving game stats: " + e.getMessage());
        }
    }

    @Override
    public void save_user(long user_id, String user_name, boolean isFirst) {
        String tableName = isFirst ? "user_first" : "user_second";
        String insertSQL = "INSERT INTO " + tableName + " (id, name) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setLong(1, user_id);
            pstmt.setString(2, user_name);

            pstmt.executeUpdate();
            System.out.println("Data has been saved to the database in " + tableName);

        } catch (SQLException e) {
            System.out.println("An error occurred while saving data: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<String> loadData() {
        return null;
    }

    @Override
    public void update(String oldData, String newData) {

    }

    @Override
    public void delete(String data) {

    }
}
