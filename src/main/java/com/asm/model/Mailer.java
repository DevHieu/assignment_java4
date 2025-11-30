package com.asm.model;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;

public class Mailer {
    public static void send(String from, String to, String subject, String body) {
        // Thông số kết nối GMail
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Đăng nhập GMail
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String username = "thuyvy240706@gmail.com";
                String password = "gjxe uyzc upfs dzuw";
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            // Tạo mail
            MimeMessage mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress(from));
            mail.setRecipients(RecipientType.TO, to);
            mail.setSubject(subject, "utf-8");
            mail.setText(body, "utf-8", "html");
            mail.setReplyTo(mail.getFrom());
            // Gửi mail
            Transport.send(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
