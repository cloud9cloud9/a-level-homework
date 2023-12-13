package com.alevel_hw2;

import java.sql.*;

public class TestJDBCLibrary {
    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/alevel",
                "postgres", "1234567890")) {

            try (Statement createTableStatement = connection.createStatement()) {
                String createTableQuery = "CREATE TABLE IF NOT EXISTS employees (" +
                        "id bigserial PRIMARY KEY," +
                        "name VARCHAR(255)," +
                        "position VARCHAR(255))";
                createTableStatement.executeUpdate(createTableQuery);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try (Statement insertStatement = connection.createStatement()) {
                String insertQuery1 = "INSERT INTO employees (name, position) VALUES ('John Doe', 'Developer')";
                String insertQuery2 = "INSERT INTO employees (name, position) VALUES ('Jane Smith', 'Manager')";
                insertStatement.executeUpdate(insertQuery1);
                insertStatement.executeUpdate(insertQuery2);
            }

            // Вибірка всіх працівників та виведення на консоль
            try (Statement selectStatement = connection.createStatement()) {
                String selectQuery = "SELECT * FROM employees";
                try (ResultSet resultSet = selectStatement.executeQuery(selectQuery)) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String position = resultSet.getString("position");
                        System.out.println("Employee ID: " + id + ", Name: " + name + ", Position: " + position);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
