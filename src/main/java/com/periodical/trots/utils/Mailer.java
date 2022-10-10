package com.periodical.trots.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

public class Mailer {

    final static String user="periodicalsite@gmail.com";//change accordingly
    final static String pass=System.getenv("MAIL_PASSWORD");
    public static final String ORDER_REPORT_PDF = "C:\\src\\main\\resources\\static\\order_report.pdf";

    public static void send(String to, String subject, String message) throws MessagingException {
        Session session = getSession(user, pass);
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage mimeMessage = new MimeMessage(session);
            // Set From: header field of the header.
            mimeMessage.setFrom(new InternetAddress(user));
            // Set To: header field of the header.
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: header field
            mimeMessage.setSubject(subject);
            // Now set the actual message
            mimeMessage.setText(message);

            System.out.println("sending...");
            // Send message
            Transport.send(mimeMessage);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void sendMailToAdminReportOrders(String to, String subject, String message) throws MessagingException {
        Session session = getSession(user, pass);
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage mimeMessage = new MimeMessage(session);
            // Set From: header field of the header.
            mimeMessage.setFrom(new InternetAddress(user));
            // Set To: header field of the header.
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: header field
            mimeMessage.setSubject(subject);
            // Now set the actual message
            mimeMessage.setText(message);

            Multipart emailContent = new MimeMultipart();

            MimeBodyPart pdfAttachment = new MimeBodyPart();
            pdfAttachment.attachFile(ORDER_REPORT_PDF);

            emailContent.addBodyPart(pdfAttachment);

            mimeMessage.setContent(emailContent);

            System.out.println("sending...");
            // Send message
            Transport.send(mimeMessage);
            System.out.println("Sent message successfully....");
        } catch (MessagingException | IOException mex) {
            mex.printStackTrace();
        }
    }

    private static Session getSession(String user, String pass) {
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.starttls.enable", "true");

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
    }
}
