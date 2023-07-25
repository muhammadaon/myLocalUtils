package com.personal.mylocalutils.service;


public class GeneratePinBlock {



    public static void main(String[] args) throws Exception {

        String pan = "2205930501152364";
        String password = "1111";

        String pinBlock = CardPinEncrypt.encrypt(pan, password);

        System.out.println("Pin Block = " + pinBlock);



    }

}
