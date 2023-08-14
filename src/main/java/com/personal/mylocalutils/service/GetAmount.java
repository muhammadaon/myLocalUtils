package com.personal.mylocalutils.service;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class GetAmount {

    public static void main(String[] args) throws Exception {

        BigDecimal rupees = new BigDecimal("47000");
        BigDecimal paisas = rupees.multiply(new BigDecimal("100"));

        String finalAmount = StringUtils.leftPad(String.valueOf(paisas), 12, "0");

        System.out.println(finalAmount);


    }

}
