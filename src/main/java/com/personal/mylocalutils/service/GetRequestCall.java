package com.personal.mylocalutils.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class GetRequestCall {

    public static void main(String[] args) {
        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Set request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type",APPLICATION_JSON_VALUE );
        headers.set("Authorization", "Bearer ywWV0hEwgu7Ani7Wsk46xox29ecmj6Vd"); // Replace "your-token" with the actual token value

        // Set request body
        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("transmissionDate", "20230608");
        requestBody.put("transmissionTime", "133429");
        requestBody.put("stan", "891687");
        requestBody.put("rrn", "489149021407");
        requestBody.put("dateLocalTran", "20230608");
        requestBody.put("timeLocalTran", "133429");
        requestBody.put("acqInstIdCode", "87003210100");
        requestBody.put("accountNumber", "03341820558");
        requestBody.put("amount", "000000600000");
        requestBody.put("terminalId", "00000051");
        requestBody.put("terminalNameLoc", "AFT Head Office");
        requestBody.put("tranCode", "WI");
        requestBody.put("subType", "01");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Create ObjectMapper and configure it
        ObjectMapper objectMapper = new ObjectMapper();

        // Create MappingJackson2HttpMessageConverter and set the ObjectMapper
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(objectMapper);

        // Add the message converter to the RestTemplate
        restTemplate.getMessageConverters().add(messageConverter);



        // Send GET request with JSON body
        ResponseEntity<String> response = restTemplate.exchange(
                "http://127.0.01:8000/AFTCustom/api/v1/transaction/memberbank/WalletInquiryPartner", // Replace with your endpoint URL
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        // Process response
        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            System.out.println("Response: " + responseBody);
        } else {
            System.err.println("Request failed with status code: " + response.getStatusCode());
        }
    }
}
