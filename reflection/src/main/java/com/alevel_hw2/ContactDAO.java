package com.alevel_hw2;

import java.sql.*;

public class ContactDAO {
    private final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS contacts (" +
            "id bigserial PRIMARY KEY," +
            "contact VARCHAR(255)," +
            "contact_type VARCHAR(255))";

    private final String CREATE_CONTACT = "INSERT INTO contacts (contact, contact_type) VALUES (?, ?)";

    private final String UPDATE_CONTACT = "UPDATE contacts SET contact = ?, contact_type = ? WHERE id = ?;";

    private final String DELETE_CONTACT = "DELETE FROM contacts WHERE id = ?";

    private final String READ_CONTACT = "SELECT * FROM contacts WHERE id = ?";

    private final String READ_ALL_CONTACT = "SELECT * FROM contacts";

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

    public void createContact(String contact, String contact_type) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CONTACT);

            preparedStatement.setString(1, contact);
            preparedStatement.setString(2, contact_type);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void readContact(long id) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_CONTACT);
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int idContact = resultSet.getInt("id");
                    String contact = resultSet.getString("contact");
                    String contact_type = resultSet.getString("contact_type");
                    System.out.println("Contact ID: " + idContact + ", Contact: " + contact + ", Contact_type: "
                            + contact_type);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void readAllContact() {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_CONTACT);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int idContact = resultSet.getInt("id");
                    String contact = resultSet.getString("contact");
                    String contact_type = resultSet.getString("contact_type");
                    System.out.println("Contact ID: " + idContact + ", Contact: " + contact + ", Contact_type: " + contact_type);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateContact(long id, String newContact, String newContactType) {
        try (Connection connection = getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CONTACT)) {
                if (newContact != null) {
                    preparedStatement.setString(1, newContact);

                    if (newContactType != null) {
                        preparedStatement.setString(2, newContactType);
                    }
                    preparedStatement.setLong(3, id);
                    preparedStatement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteContact(long id) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CONTACT);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

