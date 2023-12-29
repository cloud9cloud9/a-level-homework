package com.alevel_hw2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {

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
        final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS contacts (" +
                "id bigserial PRIMARY KEY," +
                "contact VARCHAR(255)," +
                "contact_type VARCHAR(255))";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_QUERY);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createContact(String contact, String contact_type) {
        final String CREATE_CONTACT = "INSERT INTO contacts (contact, contact_type) VALUES (?, ?)";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CONTACT);

            preparedStatement.setString(1, contact);
            preparedStatement.setString(2, contact_type);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Contact> readContact(long id) {
        List<Contact> contactList = new ArrayList<>();
        final String READ_CONTACT = "SELECT * FROM contacts WHERE id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_CONTACT);
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int idContact = resultSet.getInt("id");
                    String contact = resultSet.getString("contact");
                    String contact_type = resultSet.getString("contact_type");
                    Contact contacts = new Contact(idContact, contact, contact_type);
                    contactList.add(contacts);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contactList;
    }

    public List<Contact> readAllContact() {
        List<Contact> contactsList = new ArrayList<>();
        final String READ_ALL_CONTACT = "SELECT * FROM contacts";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_CONTACT);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int idContact = resultSet.getInt("id");
                    String contact = resultSet.getString("contact");
                    String contact_type = resultSet.getString("contact_type");
                    Contact contacts = new Contact(idContact, contact, contact_type);
                    contactsList.add(contacts);
                    for(Contact cont : contactsList){
                        System.out.println(cont);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contactsList;
    }

    public void updateContact(long id, String newContact, String newContactType) {
        final String UPDATE_CONTACT = "UPDATE contacts SET contact = ?, contact_type = ? WHERE id = ?";
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
        final String DELETE_CONTACT = "DELETE FROM contacts WHERE id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CONTACT);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

