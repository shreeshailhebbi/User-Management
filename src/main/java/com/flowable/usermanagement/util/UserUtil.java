package com.flowable.usermanagement.util;

import com.flowable.usermanagement.entity.User;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class UserUtil {

    private UserUtil() {
    }

    public static String getUnlockEmailBody(User user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hi " + user.getFirstName() + "," + user.getLastName());
        stringBuilder.append("/n");
        stringBuilder.append("Welcome to IES family,your registration is almost complete.");
        stringBuilder.append("/n");
        stringBuilder.append("Please unlock your account using below details.");
        stringBuilder.append("/n");
        stringBuilder.append("Temporary password : " + user.getPassword());
        stringBuilder.append("/n");
        stringBuilder.append("<a href='http://localhost:8080/user-management/auth/unlock-account'>Link to unlock account</a>");
        stringBuilder.append("/n");
        stringBuilder.append("Thanks,");
        stringBuilder.append("/n");
        stringBuilder.append("IES Team");
        return stringBuilder.toString();
    }

    public static String getForgoPasswordEmailBody(User user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hi " + user.getFirstName() + "," + user.getLastName());
        stringBuilder.append("/n");
        stringBuilder.append("Welcome to IES family,your password is attached.");
        stringBuilder.append("/n");
        stringBuilder.append("Please login your account using below details.");
        stringBuilder.append("/n");
        stringBuilder.append("Password : " + user.getPassword());
        stringBuilder.append("/n");
        stringBuilder.append("<a href='http://localhost:8080/user-management/auth/login'>Link to unlock account</a>");
        stringBuilder.append("/n");
        stringBuilder.append("Thanks,");
        stringBuilder.append("/n");
        stringBuilder.append("IES Team");
        return stringBuilder.toString();
    }

    public static String generateSecureRandomPassword() {
        Stream<Character> pwdStream = Stream.concat(getRandomNumbers(2), Stream.concat(getRandomSpecialChars(2), Stream.concat(getRandomAlphabets(2, true), getRandomAlphabets(4, false))));
        List<Character> charList = pwdStream.collect(Collectors.toList());
        Collections.shuffle(charList);
        String password = charList.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }

    private static Stream<Character> getRandomAlphabets(int count, boolean upperCase) {
        Random random = new SecureRandom();
        IntStream characters;
        if (upperCase) {
            characters = random.ints(count, 65, 90);
        } else {
            characters = random.ints(count, 97, 122);
        }
        return characters.mapToObj(data -> (char) data);
    }

    private static Stream<Character> getRandomNumbers(int count) {
        Random random = new SecureRandom();
        IntStream numbers = random.ints(count, 48, 57);
        return numbers.mapToObj(data -> (char) data);
    }

    private static Stream<Character> getRandomSpecialChars(int count) {
        Random random = new SecureRandom();
        IntStream specialChars = random.ints(count, 33, 45);
        return specialChars.mapToObj(data -> (char) data);
    }
}
