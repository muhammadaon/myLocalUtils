package com.personal.mylocalutils.service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class NIFTePay {

    // This map stores unsorted values
    static SortedMap<String, String> map = new TreeMap<>();
    static SortedMap<String, String> map1 = new TreeMap<>();

    public static String secureSecret = "yrcHe20WPbE=";

    // Function to check String is not empty or not null
    public static boolean isNotNullOrEmpty(String str){
        if(str != null || !str.isEmpty()){
            return true;
        }
        else
            return false;
    }

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String hmacWithJava(String algorithm, String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKeySpec);
        return bytesToHex(mac.doFinal(data.getBytes()));
    }

    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < buf.length; ++i) {
            String hex = Integer.toHexString(buf[i] & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }

    public static String CalculateHMACSHA256(SortedMap<String, String> ParamList) {
        Charset utf8 = Charset.forName("UTF-8");

        SortedMap<String, String> sortedMap = ParamList;
        StringBuilder sb = new StringBuilder();
        sortedMap.forEach((key, value) -> {
            if (null != value && value.length() > 0) {
                sb.append(value + "&");
            }
        });
        sb.delete(sb.length() - 1, sb.length());
        sb.insert(0, secureSecret + "&");

        String hexHash = "";

        byte[] utf8Bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
        ByteBuffer encode = utf8.encode(secureSecret);

        try {
            byte[] iso8859_1 = new String(utf8Bytes, "UTF-8").getBytes("ISO-8859-1");
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(encode.array(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            byte[] bytes = sha256_HMAC.doFinal(iso8859_1);
            hexHash = parseByte2HexStr(bytes);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
            System.out.println("Exception");
        }
        return hexHash;

    }


    public static void main(String[] args) throws Exception {

        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Using today's date

        int dayOfWeekNum = c.get(Calendar.DAY_OF_WEEK);

        DateFormat formatter = new SimpleDateFormat("EEEE");
        String dayOfWeekString = formatter.format(c.getTime());

        c.add(Calendar.DATE, 3); // Adding 3 days




        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());

        System.out.println("timeStamp = " + timeStamp);

        String expiryTimeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(c.getTime());
        System.out.println("expiryTimeStamp = " + expiryTimeStamp);

        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String rand = String.format("%06d", number);

        String txnRefNo = timeStamp+rand;
        System.out.println("tranRefNum = " + txnRefNo);

        map.put("pp_Language", "EN");
        map.put("pp_BankID", "TBANK");
        map.put("pp_ProductID", "1234567890");
        map.put("pp_Amount", "15000");
        map.put("pp_TxnCurrency", "PKR");
        map.put("pp_TxnDateTime", timeStamp);
        map.put("pp_TxnExpiryDateTime", expiryTimeStamp);
        map.put("pp_BillReference", "bill Ref");
        map.put("pp_Description", "Send otp");
        map.put("pp_ReturnURL", "https://digitt.com.pk/niftepay-postback/");
        map.put("pp_AccountNo", "78882000001111");
        map.put("pp_CNIC", "4234029847513");
        map.put("pp_MerchantID", "AFT001");
        map.put("pp_Password", "zl0grQ0mMNE=");
        map.put("pp_SubMerchantID", "");
        map.put("pp_TxnRefNo", txnRefNo);
        map.put("pp_TxnType", "ACC");
        map.put("pp_Version", "1.1");


        String secureHash = CalculateHMACSHA256(map);

        System.out.println("Secure Hash = " + secureHash);


        map1.put("pp_Language","EN");
        map1.put("pp_MerchantID","AFT001");
        map1.put("pp_Password","zl0grQ0mMNE=");
        map1.put("pp_SubMerchantID","");
        map1.put("pp_OTP","242433");
        map1.put("pp_TxnType","ACC");
        map1.put("pp_BankID","TBANK");
        map1.put("pp_Version","1.1");
        map1.put("pp_TxnRefNo","20230529190112237259");
        map1.put("pp_RetreivalReferenceNo","314919730087");

        String secureHash1 = CalculateHMACSHA256(map1);

        System.out.println("Secure Hash1 = " + secureHash1);


        System.out.println("Day Of Week Num = "+ dayOfWeekNum);
        System.out.println("Day Of Week String = " + dayOfWeekString);

    }


}



