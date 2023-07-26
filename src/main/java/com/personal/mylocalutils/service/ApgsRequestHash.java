package com.personal.mylocalutils.service;

import okhttp3.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class ApgsRequestHash {

    public static void main(String[] args) {
        String url = "https://sandbox.bankalfalah.com/HS/HS/HS";

        int bankorderId = (int) (Math.random() * 1786612);

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
        String HS_TransactionReferenceNumber = String.valueOf(bankorderId);
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

        System.out.println(mapString);


        try {
            byte[] key1Bytes = Key1.getBytes();
            byte[] key2Bytes = Key2.getBytes();

            SecretKeySpec secretKeySpec1 = new SecretKeySpec(key1Bytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(key2Bytes);

            Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec1, ivSpec);

            byte[] encryptedData = aesCipher.doFinal(mapString.getBytes());



            String hashRequest = Base64.getEncoder().encodeToString(encryptedData);

            System.out.println(hashRequest);

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
            } else {
                System.out.println("Request failed: " + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
