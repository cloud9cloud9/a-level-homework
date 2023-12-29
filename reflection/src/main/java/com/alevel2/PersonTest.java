package com.alevel2;

public class PersonTest {
    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        PersonFactory personFactory = new PersonFactory();
        Person person = personFactory.createPerson("John", 21, "Kyiv/ Ukraine");
        personFactory.printFields(person);
    }
}