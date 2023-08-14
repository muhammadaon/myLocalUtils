package com.personal.mylocalutils.service;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class ModifyHtml {

    public static String modifyHTMLWithAmount(String amount, String trxRefNumber) {
        try {
            // Load the HTML file from resources folder
            InputStream inputStream = ModifyHtml.class.getResourceAsStream("/Alfalah.html");
            Document doc = Jsoup.parse(inputStream, "UTF-8", "");

            // Find the TransactionAmount input element and set its value
            Element transactionAmountInput = doc.selectFirst("#TransactionAmount");
            if (transactionAmountInput != null) {
                transactionAmountInput.val(amount);
            } else {
                System.out.println("TransactionAmount not found");
            }

            // Find the HS_TransactionReferenceNumber input element and set its value
            Element hS_TransactionReferenceNumberInput = doc.selectFirst("#HS_TransactionReferenceNumber");
            if (hS_TransactionReferenceNumberInput != null) {
                hS_TransactionReferenceNumberInput.val(trxRefNumber);
            } else {
                System.out.println("HS_TransactionReferenceNumber not found");
            }

            // Find the TransactionReferenceNumber input element and set its value
            Element transactionReferenceNumberInput = doc.selectFirst("#TransactionReferenceNumber");
            if (transactionReferenceNumberInput != null) {
                transactionReferenceNumberInput.val(trxRefNumber);
            } else {
                System.out.println("TransactionReferenceNumber not found");
            }

            // Return the modified HTML as a string
            return doc.toString();
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // Call the modifyHTMLWithAmount method to get the modified HTML
        String amount = "2000";
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String rand = String.format("%06d", number);
        String txnRefNo = timeStamp + rand;
        System.out.println("tranRefNum = " + txnRefNo);
        String modifiedHTML = modifyHTMLWithAmount(amount, txnRefNo); // Provide the desired amount

        // Print the modified HTML
        System.out.println(modifiedHTML);
    }
}
