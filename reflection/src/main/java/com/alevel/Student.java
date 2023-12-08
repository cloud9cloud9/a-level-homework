package com.alevel;

public class Student {
    @ShowInfo(show = true)
    private String firstName;
    @ShowInfo(show = false)
    private String lastName;
    @ShowInfo(show = false)
    private int age;
    @ShowInfo(show = true)
    private int examScore;

    public Student(String firstName, String lastName, int age, int examScore) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.examScore = examScore;
    }


    private void passExamSuccessfully() {
        this.examScore = 100;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", examScore=" + examScore +
                '}';
    }
}