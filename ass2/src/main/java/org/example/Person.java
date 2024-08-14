package org.example;

import java.util.Set;

public class Person //abstract class Person
{
    private String firstName;
    private String secondName;
    private Integer age;
    private String gender;

    private static final Set<String> validGenders = Set.of("Woman", "Man", "Non-binary | gender diverse", "Prefer not to say", "Other");

    //    public Person(){}
    public Person(String firstName, String secondName, Integer age, String gender){
        setFirstName(firstName);
        setSecondName(secondName);
        setAge(age);
        setGender(gender);
    }

    public int getAge() {
        return age;
    }

    public void setAge(Integer age) {
        if (age == null) {
            throw new IllegalArgumentException("Age cannot be null.");
        }
        if ( age >= 0 && age <= 150 ) {  // 假定150岁为合理的最大年龄
            this.age = age;
        } else {
            throw new IllegalArgumentException("Age must be between 0 and 150.");
        }
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (validGenders.contains(gender)) {
            this.gender = gender;
        } else {
            throw new IllegalArgumentException("Invalid gender provided. Choose from 'Woman', 'Man', 'Non-binary | gender diverse', 'Prefer not to say', 'Other'.");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setFirstName(String firstName) {
        if (isValidName(firstName)) {
            this.firstName = firstName;
        } else {
            throw new IllegalArgumentException("First name must start with a letter and only contain alphabet characters.");
        }
    }

    public void setSecondName(String secondName) {
        if (isValidName(secondName)) {
            this.secondName = secondName;
        } else {
            throw new IllegalArgumentException("Second name must start with a letter and only contain alphabet characters.");
        }
    }

    private boolean isValidName(String name) {
        return name != null && !name.isEmpty() && Character.isLetter(name.charAt(0)) && name.matches("[a-zA-Z]+");
    }

    @Override
    public String toString()
    {
        return "Person{" +
                "firstName='" + getFirstName() + '\'' +
                ", secondName='" + getSecondName() + '\'' +
                ", age=" + getAge() +
                ", gender='" + getGender() + '\'' +
                '}';
    }
}
