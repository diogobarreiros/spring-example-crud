package com.spring.example.crud.utils;

import com.github.javafaker.Faker;
import com.spring.example.crud.domain.models.entity.Role;
import com.spring.example.crud.domain.models.entity.User;

import java.util.List;
import java.util.Locale;

import static com.spring.example.crud.domain.builders.UserBuilder.createUser;

public class Random {
        
    private static final java.util.Random RANDOM = new java.util.Random();
    public static final Faker FAKER = new Faker(new Locale("pt", "BR"));

    public static Integer between(Integer min, Integer max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static <T> T getRandomElement(List<T> itens) {
        return itens.get(RANDOM.nextInt(itens.size()));
    }

    public static String code() {
        return String.format("%s%s%s%s", between(0, 9), between(0, 9), between(0, 9), between(0, 9));
    }

    public static String password() {
        return FAKER.regexify("[a-z]{5,13}[1-9]{1,5}[A-Z]{1,5}[#?!@$%^&*-]{1,5}");
    }

    public static User randomUser() {
        return randomUser(List.of());
    }

    public static User randomUser(List<Role> roles) {
        var builder = createUser(FAKER.name().fullName())
            .setEmail(FAKER.internet().safeEmailAddress())
            .setPassword(password());
        
        roles.forEach(builder::addRole);
        
        return builder.build();
    }
}
