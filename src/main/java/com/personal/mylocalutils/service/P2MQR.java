package com.personal.mylocalutils.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mastercard.mpqr.pushpayment.enums.AdditionalDataTag;
import com.mastercard.mpqr.pushpayment.model.*;
import net.glxn.qrgen.QRCode;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;


public class P2MQR {
        public static void main(String[] args) {

            //construct the PushPaymentData object
            PushPaymentData pushPaymentData = new PushPaymentData();
            try {
                // Set required properties on push payment data e.g pushPaymentData.payloadFormatIndicator = "01"

                // Payload format indicator
                pushPaymentData.setPayloadFormatIndicator("01");

                // Point of initiation method
                pushPaymentData.setValue("01", "11");

                // Merchant identifier MasterCard with tag `04`
//                pushPaymentData.setMerchantIdentifierMastercard04("501234560000339871");

               // 17 - 25 for merchant identifier EMVCO
//            pushPaymentData.setValue("27","923115014537");

//            Merchant identifier OPS with tag `27`
                String BIC = "0108DGTTPKKA";
                String IBAN = "0224PK73FAYS3555391000000000";
                String tagValue = "0032"+ RandomID.randomAlphaNumeric(32)+ BIC+ IBAN;

            pushPaymentData.setValue("28", tagValue);

//                Merchant Identifier data 26-51
//            String rootTag = "27";
//            MAIData maiData = new MAIData(rootTag);
//            maiData.setAID("AID0349509H");
//            maiData.setValue("01", "PNS93484jf");
//            maiData.setValue("02", "PNSDyn8494738");
//            pushPaymentData.setDynamicMAIDTag(maiData);

//             Merchant category code
                pushPaymentData.setMerchantCategoryCode("9999");

                // Transaction currency code
                pushPaymentData.setTransactionCurrencyCode("586");

                //Country Code
//            pushPaymentData.setCountryCode("PK");

                // Transaction amount
                //pushPaymentData.setTransactionAmount(83.80);

//            Serializable tipType = PushPaymentData.TipConvenienceIndicator.PERCENTAGE_CONVENIENCE_FEE;
//            if (PushPaymentData.TipConvenienceIndicator.PROMPTED_TO_ENTER_TIP.equals(tipType)) {
//                // Tip or convenience indicator
//                pushPaymentData.setTipOrConvenienceIndicator("01");
//
//            } else if (PushPaymentData.TipConvenienceIndicator.FLAT_CONVENIENCE_FEE.equals(tipType)) {
//                // Tip or convenience indicator
//                pushPaymentData.setTipOrConvenienceIndicator("02");
//                // Value of convenience fee fixed
//                pushPaymentData.setValueOfConvenienceFeeFixed(10);
//
//            } else if (PushPaymentData.TipConvenienceIndicator.PERCENTAGE_CONVENIENCE_FEE.equals(tipType)) {
//                // Tip or convenience indicator
//                pushPaymentData.setTipOrConvenienceIndicator("03");
//                // Value of convenience fee percentage
//                pushPaymentData.setValueOfConvenienceFeePercentage(5);
//            }

                // Country code
                pushPaymentData.setCountryCode("PK");

                // Merchant name
                pushPaymentData.setMerchantName("The Clinic Pharmacy");

                // Merchant city
                pushPaymentData.setMerchantCity("Lahore");

                // Postal code
                //pushPaymentData.setPostalCode("56748");

                //Additional data
                AdditionalData addData = new AdditionalData();
//            addData.setBillNumber("12345");
                addData.setStoreId("57723");
                addData.setTerminalId("52967");
//            addData.setValue("50", "1122334455");

//            addData.setAdditionalDataRequest("ME");
//              addData.setLoyaltyNumber("***");

                //pushPaymentData.s
//                String rootSubTag = "50";
//                UnrestrictedData additionalUnrestrictedData = new UnrestrictedData(rootSubTag);
//                additionalUnrestrictedData.setAID("GUI123");
//                additionalUnrestrictedData.setValue("01", "CONT");
//                additionalUnrestrictedData.setValue("02", "DYN6");
//                addData.setValue(rootSubTag, additionalUnrestrictedData);


//                String rootSubTag = "51";
//                additionalUnrestrictedData = new UnrestrictedData(rootSubTag);
//                additionalUnrestrictedData.setAID("GUI2");
//                additionalUnrestrictedData.setValue("01", "CON");
//                additionalUnrestrictedData.setValue("02", "DYN22");
//                addData.setValue(rootSubTag, additionalUnrestrictedData);

                pushPaymentData.setAdditionalData(addData);

                // Language Data
//                LanguageData langData = new LanguageData();
//                langData.setLanguagePreference("EN-US");
//                langData.setAlternateMerchantCity("beij");
//                langData.setAlternateMerchantName("best");
//                pushPaymentData.setLanguageData(langData);

                //unrestricted data
//                rootTag = "88";
//                UnrestrictedData unrestrictedData = new UnrestrictedData(rootTag);
//                unrestrictedData.setAID("GUI12319494");
//                unrestrictedData.setValue("01", "CONT7586F");
//                unrestrictedData.setValue("02", "DYN647382");
//                pushPaymentData.setValue(rootTag, unrestrictedData);

            } catch (Exception e) {
                //do something for the exception
                System.out.println("1st Exception");
            }
            try {
                //QR String
                String qrContent = pushPaymentData.generatePushPaymentString();
//                Base64.Encoder encoder = Base64.getEncoder();
//                String encodedString = encoder.encodeToString(qrContent.getBytes());
//                System.out.println(encodedString);
//                Base64.Decoder decoder = Base64.getDecoder();
//                byte[] bytes = decoder.decode(encodedString);

//                System.out.println(new String(bytes));

                ByteArrayOutputStream qrByteOs = new ByteArrayOutputStream();
//
//                //Convert to byteStream
                QRCode.from(qrContent).withSize(500,500).writeTo(qrByteOs);
//                System.out.println(qrByteOs);
//                Base64.Encoder encoder1 = Base64.getEncoder();
//                String encodedString1 = encoder1.encodeToString(qrByteOs.toByteArray());
//                System.out.println(qrContent);
//                System.out.println(qrContent.length());
//                System.out.println(qrByteOs);
//                System.out.println(encodedString1);
//                System.out.println(encodedString1.length());
                ByteArrayInputStream qrByteIs = new ByteArrayInputStream(qrByteOs.toByteArray());
//                System.out.println(qrByteOs);


//                //Convert byteStream to Image
                BufferedImage qrImage = ImageIO.read(qrByteIs);

//                String xmlString = Base64.getEncoder().encodeToString(qrImage.toString().getBytes());
//                System.out.println(xmlString);

                //Saving Image
                ImageIO.write(qrImage, "png", new File("/Users/aft/Downloads/QR"));

                byte[] fileContent = FileUtils.readFileToByteArray(new File("/Users/aft/Downloads/QR"));
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                System.out.println(encodedString);



            } catch (Exception e) {
                //do something for the exception
                System.out.println("2nd Exception");
                System.out.println("Exception: " + e.getMessage());
            }
        }

}
