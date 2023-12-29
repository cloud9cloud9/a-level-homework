package com.alevel_hw2;

import java.sql.*;

public class EmployeeDAO {

    private final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS employees (" +
            "id bigserial PRIMARY KEY," +
            "name VARCHAR(255)," +
            "position VARCHAR(255))";

    private final String CREATE_EMPLOYEE = "INSERT INTO employees (name, position) VALUES (?, ?)";

    private final String UPDATE_EMPLOYEE = "UPDATE employees SET name = ?, position = ?\n" +
            "WHERE id = ?;";

    private final String DELETE_EMPLOYEE = "DELETE FROM employees WHERE id = ?";

    private final String READ_EMPLOYEE = "SELECT * FROM employees WHERE id = ?";

    private final String READ_ALL_EMPLOYEE = "SELECT * FROM employees";

    private Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/alevel",
                    "postgres", "postgres");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTable() {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_QUERY);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createEmployee(String name, String position) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_EMPLOYEE);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, position);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void readEmployee(long id) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_EMPLOYEE);
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int idEmployee = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String position = resultSet.getString("position");
                    System.out.println("Employee ID: " + idEmployee + ", Name: " + name + ", Position: " + position);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void readAllEmployee() {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_EMPLOYEE);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String position = resultSet.getString("position");
                    System.out.println("Employee ID: " + id + ", Name: " + name + ", Position: " + position);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEmployee(long id, String newName, String newPosition) {
        try (Connection connection = getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE)) {
                if (newName != null) {
                    preparedStatement.setString(1, newName);

                    if (newPosition != null) {
                        preparedStatement.setString(2, newPosition);
                    }
                    preparedStatement.setLong(3, id);
                    preparedStatement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEmoployee(long id) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
