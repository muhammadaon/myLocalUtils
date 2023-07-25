package com.personal.mylocalutils.service;

import java.util.Random;

public class RandomNumberGenerator {
    public static void main(String[] args) {
        String key = generateRandomKey(16);
        System.out.println("Random Key: " + key);
        System.out.println("Key Length: " + key.length());
    }

    private static String generateRandomKey(int length) {
        Random random = new Random();
        StringBuilder keyBuilder = new StringBuilder();

        // Generate the first digit (1-9) separately to avoid starting with zero
        int firstDigit = random.nextInt(9) + 1;
        keyBuilder.append(firstDigit);

        // Generate the rest of the digits
        for (int i = 1; i < length; i++) {
            int digit = random.nextInt(10);
            keyBuilder.append(digit);
        }

        return keyBuilder.toString();
    }
}
