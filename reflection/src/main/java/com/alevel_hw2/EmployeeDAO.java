package com.alevel_hw2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

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
        final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS employees (" +
                "id bigserial PRIMARY KEY," +
                "name VARCHAR(255)," +
                "position VARCHAR(255))";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_QUERY);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createEmployee(String name, String position) {
        final String CREATE_EMPLOYEE = "INSERT INTO employees (name, position) VALUES (?, ?)";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_EMPLOYEE);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, position);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Employee> readEmployee(long id) {
        List<Employee> employeeList = new ArrayList<>();
        final String READ_EMPLOYEE = "SELECT * FROM employees WHERE id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_EMPLOYEE);
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int idEmployee = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String position = resultSet.getString("position");
                    Employee employee = new Employee(idEmployee, name, position);
                    employeeList.add(employee);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }

    public List<Employee> readAllEmployee() {
        List<Employee> employeeList = new ArrayList<>();
        final String READ_ALL_EMPLOYEE = "SELECT * FROM employees";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_EMPLOYEE);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String position = resultSet.getString("position");
                    Employee employee = new Employee(id, name, position);
                    employeeList.add(employee);
                    for(Employee employ : employeeList){
                        System.out.println(employ);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }

    public void updateEmployee(long id, String newName, String newPosition) {
        final String UPDATE_EMPLOYEE = "UPDATE employees SET name = ?, position = ?\n" +
                "WHERE id = ?;";
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
        final String DELETE_EMPLOYEE = "DELETE FROM employees WHERE id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
