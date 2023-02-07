package org.example;

public class UserGenerator {
    public static User getUser() {
        return new User(DataGenerator.getRandomEmail(), DataGenerator.getRandomStringPhone(),
                DataGenerator.getRandomString());
    }

    public static User getUserWithoutEmail() {
        return new User ("", DataGenerator.getRandomStringPhone(), DataGenerator.getRandomString());
    }

    public static User getUserWithoutPassword() {
        return new User (DataGenerator.getRandomEmail(), "", DataGenerator.getRandomString());
    }
}
