package com.personal.mylocalutils.service;


import java.util.HashMap;
import java.util.Map;


public class MapToString {

    public static void main(String[] args) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("consumerNum", "2344123455");
        dataMap.put("partnerTrxId", "202303291415167890324");

        String convertedString = convertMapToString(dataMap);
        System.out.println("Converted Map to String: " + convertedString);
    }

    public static String convertMapToString(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append(", ");
        }

        // Remove the trailing comma and space if there are entries
        if (!map.isEmpty()) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }

}