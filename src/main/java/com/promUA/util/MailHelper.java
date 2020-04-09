package com.promUA.util;

import io.qameta.allure.Step;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

public class MailHelper {

    private final String USERNAME;
    private final String PASSWORD;
    private Store store;
    private Folder emailFolder;

    public MailHelper() {
        this.USERNAME = AppProperties.getEmailLogin();
        this.PASSWORD = AppProperties.getEmailPassword();
    }

    public Message[] getMails() {
        try {
            store = this.getStore();
            // create the folder object and open it

            emailFolder = store.getFolder("promUA");
            emailFolder.open(Folder.READ_ONLY);
            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();

            return messages;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("NO MESSAGE RECEIVED");
        return new Message[0];
    }

    private void closeStore() {
        try {
            emailFolder.close(false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        if (store != null && !store.isConnected()) {
            try {
                store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    }

    private Store getStore() throws MessagingException {
        Properties properties;
        Session session;
        Store store;
        properties = new Properties();

        try {
            properties.load(MailHelper.class.getClassLoader().getResourceAsStream("mail.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME,
                                PASSWORD);
                    }
                });
        store = session.getStore("imaps");
        store.connect();
        return store;
    }

    @Step("Find messages in mailbox for receiver {0}")
    public String getMessageSubjectForReceiver(String receiverEmail) {
        Message[] messages = this.getMails();
        try {
            for (Message message :
                    messages) {
                for (Address receiverAddress :
                        message.getAllRecipients()) {
                    String addressToParse = receiverAddress.toString().replace("<", "");
                    addressToParse = addressToParse.replace(">", "");
                    for (String partAdress : addressToParse.split(" ")) {
                        if (partAdress.equals(receiverEmail)) {
                            return message.getSubject();
                        }
                    }
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            closeStore();
        }
        return "";
    }
}
