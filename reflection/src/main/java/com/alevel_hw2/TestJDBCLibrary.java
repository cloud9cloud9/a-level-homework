package com.alevel_hw2;

public class TestJDBCLibrary {
    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        employeeDAO.createTable();
        //employeeDAO.createEmployee("Soly", "Manager");
        employeeDAO.readEmployee(1);
        //employeeDAO.updateEmployee(5, "Camlton", "Java_Developer");
        employeeDAO.readAllEmployee();
        //employeeDAO.deleteEmoployee(3);

        ContactDAO contactDAO = new ContactDAO();
        contactDAO.createTable();
        //contactDAO.createContact("gmail@gmail.com", "email");
        contactDAO.readContact(1);
        //contactDAO.updateContact(1, "ggggg@gmail.com", "email");
        contactDAO.readAllContact();
        //contactDAO.deleteContact(3);

    }
}
