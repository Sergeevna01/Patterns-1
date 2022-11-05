package ru.netology.delivery.test;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("ru"));

    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        LocalDate meetingDat = LocalDate.now().plusDays(3 + shift);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return meetingDat.format(formatter);
    }

    public static String generateCity() {
        return faker.address().city();
    }

    public static String generateName(String locale) {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        return firstName + ' ' + lastName;
    }

    public static String generatePhone() {
        return faker.phoneNumber().phoneNumber();
    }

}
