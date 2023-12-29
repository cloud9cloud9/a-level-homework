package com.alevel2;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class PersonFactory {
    public Person createPerson(String name, int age, String address){
        try {
            Class<?> personClass = Class.forName("com.alevel2.Person");
            Constructor<?> constructor = personClass.getConstructor(String.class, int.class, String.class);
            return (Person) constructor.newInstance(name, age, address);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                 InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    public void printFields(Object obj) throws IllegalAccessException {
        Field[] field = obj.getClass().getDeclaredFields();
        for(Field fields : field){
            fields.setAccessible(true);
            System.out.println(fields.get(obj));
        }
    }
}
