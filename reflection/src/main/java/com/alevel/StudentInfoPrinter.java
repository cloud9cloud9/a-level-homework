package com.alevel;


import java.lang.reflect.Field;

public class StudentInfoPrinter {
    public void printStudentInfo(Student student) {
        try {
            Class<? extends Student> aClass = student.getClass();
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                ShowInfo showInfoAnnotation = field.getAnnotation(ShowInfo.class);
                if (showInfoAnnotation != null && showInfoAnnotation.show()) {
                    field.setAccessible(true);
                    Object fieldValue =  field.get(student);
                    if (fieldValue != null) {
                        System.out.println(field.getName() + " : " +
                                fieldValue);
                    }
                }
            }
        } catch (SecurityException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
