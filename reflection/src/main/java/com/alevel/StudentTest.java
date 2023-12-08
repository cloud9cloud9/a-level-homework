package com.alevel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StudentTest {
    public static void main(String[] args) {
        Student student = new Student("Vlad", "Luthor", 21, 95);
        StudentInfoPrinter studentInfoPrinter = new StudentInfoPrinter();
        studentInfoPrinter.printStudentInfo(student);

        try {
            Method method = student.getClass().getDeclaredMethod("passExamSuccessfully");
            method.setAccessible(true);
            method.invoke(student);
            Field field = student.getClass().getDeclaredField("examScore");
            field.setAccessible(true);
            Object o = field.get(student);
            System.out.println("new exam Score of that students : " + o);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
