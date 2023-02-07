package org.example;
import org.apache.commons.lang3.RandomStringUtils;

public class DataGenerator {
    public static String getRandomString() {
        return RandomStringUtils.randomAlphabetic(5);
    }
    public static String getRandomEmail() {
        return (RandomStringUtils.randomAlphabetic(5) + "@yandex.ru");
    }
    public static int getRandomNumber() {
        return (int) (Math.random() * 5);
    }

    public static String getRandomStringPhone() {
        return RandomStringUtils.randomNumeric(10);
    }

    public static String getRandomDate() {
        return ((int) (2021 + Math.random() * 3) + "-" + (int) (1 + Math.random() * 6) + "-"
                + (int) (Math.random() * 30));
    }
}
