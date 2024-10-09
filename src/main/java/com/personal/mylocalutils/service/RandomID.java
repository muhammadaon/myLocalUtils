package com.personal.mylocalutils.service;

import java.time.LocalDateTime;
import java.util.Random;

public class RandomID {
    private static Random rand = new Random();

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String getRequestId() {
        return String.valueOf((rand.nextInt(999999999) * LocalDateTime.now().getNano()) + 1).replace('-',' ').substring(0);
    }



    public static void main(String []args){


        System.out.println(getRequestId());

    }

}
