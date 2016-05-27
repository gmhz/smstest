package com.test.smsgateapi;

/**
 * Created by admin on 27.05.2016.
 */
public class XmlMessage {
    private static String LOGIN = "tg324gf2";
    private static String PASSWORD = "qvofxcmM";
    private static String SENDER = "996772673639";

    private String[] phones;
    private String messageText;

    public String getReadyMessageXMLString() {

        String xmlPhones = "";
        for (String p : phones)
            xmlPhones += "<phone>" + p + "</phone>";

        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<message>" +
                "<login>" + LOGIN + "</login>" +
                "<pwd>" + PASSWORD + "</pwd>" +
                "<sender>" + SENDER + "</sender>" +
                "<text>" + messageText + "</text>" +
                "<phones>" + xmlPhones + "</phones>" +
//                "<test>1</test>"+
                "</message>";

        return xmlString;
    }

    public void setPhones(String[] phones) {
        this.phones = phones;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public XmlMessage(String[] phones, String messageText) {
        this.phones = phones;
        this.messageText = messageText;
    }
}
