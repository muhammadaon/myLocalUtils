package com.personal.mylocalutils.service;

import okhttp3.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

public class ApgsRequestHash {

    public static void main(String[] args) {
        String url = "https://sandbox.bankalfalah.com/HS/HS/HS";
        String ssoUrl = "https://sandbox.bankalfalah.com/SSO/SSO/SSO";

//        int bankorderId = (int) (Math.random() * 1786612);
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String rand = String.format("%06d", number);
        String txnRefNo = timeStamp + rand;
        System.out.println("tranRefNum = " + txnRefNo);

        String Key1 = "CBsP9aCysqvQauvC";
        String Key2 = "1074106468866678";
        String HS_ChannelId = "1001";
        String HS_MerchantId = "19285";
        String HS_StoreId = "024744";
        int HS_IsRedirectionRequest = 0;
        String HS_ReturnURL = "https://digitt.com.pk/APG";
        String HS_MerchantHash = "OUU362MB1urX4Ekr68UNt95etaRWBCHx6WsZKh9WHehvFzk4yqF7CA==";
        String HS_MerchantUsername = "yvygil"; // Replace with your actual value
        String HS_MerchantPassword = "5+pyffR7iWxvFzk4yqF7CA=="; // Replace with your actual value
        String HS_TransactionReferenceNumber = txnRefNo;
        String cipher = "AES/CBC/PKCS5Padding";


        String mapString =
                "HS_ChannelId=" + HS_ChannelId +
                        "&HS_IsRedirectionRequest=" + HS_IsRedirectionRequest +
                        "&HS_MerchantId=" + HS_MerchantId +
                        "&HS_StoreId=" + HS_StoreId +
                        "&HS_ReturnURL=" + HS_ReturnURL +
                        "&HS_MerchantHash=" + HS_MerchantHash +
                        "&HS_MerchantUsername=" + HS_MerchantUsername +
                        "&HS_MerchantPassword=" + HS_MerchantPassword +
                        "&HS_TransactionReferenceNumber=" + HS_TransactionReferenceNumber;

        System.out.println("String for handshake call" + mapString);


        try {
            byte[] key1Bytes = Key1.getBytes();
            byte[] key2Bytes = Key2.getBytes();

            SecretKeySpec secretKeySpec1 = new SecretKeySpec(key1Bytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(key2Bytes);

            Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec1, ivSpec);

            byte[] encryptedData = aesCipher.doFinal(mapString.getBytes());

            String hashRequest = Base64.getEncoder().encodeToString(encryptedData);

            System.out.println("Hash Request 1 = " + hashRequest);

            // The data you want to send via POST
            Map<String, String> fields = new HashMap<>();
            fields.put("HS_ChannelId", HS_ChannelId);
            fields.put("HS_IsRedirectionRequest", String.valueOf(HS_IsRedirectionRequest));
            fields.put("HS_MerchantId", HS_MerchantId);
            fields.put("HS_StoreId", HS_StoreId);
            fields.put("HS_ReturnURL", HS_ReturnURL);
            fields.put("HS_MerchantHash", HS_MerchantHash);
            fields.put("HS_MerchantUsername", HS_MerchantUsername);
            fields.put("HS_MerchantPassword", HS_MerchantPassword);
            fields.put("HS_TransactionReferenceNumber", HS_TransactionReferenceNumber);
            fields.put("HS_RequestHash", hashRequest);

            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : fields.entrySet()) {
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
            RequestBody requestBody = formBodyBuilder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
                System.out.println(result);

                // You can handle the JSON response as needed here.
                // For example, you can parse the JSON and extract the AuthToken.
                // To parse JSON, you can use libraries like Gson or Jackson.

                /***code to call the Page redirection request is below
                 */

                String currency = "PKR";
                String IsBIN = "0";
                String TransactionTypeId = "3";
                String TransactionAmount = "150";
                String RequestHash1 = null;

                JSONObject jsonResponse = new JSONObject(result);
                boolean success = jsonResponse.getBoolean("success");
                String authToken = jsonResponse.getString("AuthToken");
                String returnURL = jsonResponse.getString("ReturnURL");

                String mapStringSSo = "AuthToken=" + authToken
                        + "&RequestHash=" + null
                        + "&ChannelId=" + HS_ChannelId
                        + "&Currency=" + currency
                        + "&IsBIN=" + IsBIN
                        + "&ReturnURL=" + HS_ReturnURL
                        + "&MerchantId=" + HS_MerchantId
                        + "&StoreId=" + HS_StoreId
                        + "&MerchantHash=" + HS_MerchantHash
                        + "&MerchantUsername=" + HS_MerchantUsername
                        + "&MerchantPassword=" + HS_MerchantPassword
                        + "&TransactionTypeId=" + TransactionTypeId
                        + "&TransactionReferenceNumber=" + HS_TransactionReferenceNumber
                        + "&TransactionAmount=" + TransactionAmount;

                System.out.println("String for SSO Call" + mapStringSSo);


                byte[] encryptedDataSSo = aesCipher.doFinal(mapStringSSo.getBytes());

                String hashRequest2 = Base64.getEncoder().encodeToString(encryptedDataSSo);

                System.out.println("Hash Request 2 = " + hashRequest2);

                 // The data you want to send via POST
                Map<String, String> fields1 = new HashMap<>();
                fields1.put("AuthToken", authToken);
                fields1.put("RequestHash", hashRequest2);
                fields1.put("ChannelId", HS_ChannelId);
                fields1.put("Currency", currency);
                fields1.put("IsBIN", IsBIN);
                fields1.put("ReturnURL", HS_ReturnURL);
                fields1.put("MerchantId", HS_MerchantId);
                fields1.put("StoreId", HS_StoreId);
                fields1.put("MerchantHash", HS_MerchantHash);
                fields1.put("MerchantUsername", HS_MerchantUsername);
                fields1.put("MerchantPassword", HS_MerchantPassword);
                fields1.put("TransactionTypeId", TransactionTypeId);
                fields1.put("TransactionReferenceNumber", txnRefNo);
                fields1.put("TransactionAmount", TransactionAmount);



                for (Map.Entry<String, String> entry : fields1.entrySet()) {
                    formBodyBuilder.add(entry.getKey(), entry.getValue());
                }
                RequestBody requestBody2 = formBodyBuilder.build();

                Request request2 = new Request.Builder()
                        .url(ssoUrl)
                        .post(requestBody2)
                        .build();

                Response response2 = client.newCall(request2).execute();

                if (response2.isSuccessful()) {
                    String result2 = response2.body().string();
                    System.out.println(result2);
                } else {
                    System.out.println("Page Redirection Request failed: " + response.message());
                }


            } else {
                System.out.println("Request failed: " + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

