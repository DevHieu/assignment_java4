package com.asm.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class XMailer {
  public static boolean send(String to, String subject, String body) {
    final String username = "hieudd2090@gmail.com";
    final String password = "nqhuaimewllmvfcu";

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");

    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });

    try {
      MimeMessage mail = new MimeMessage(session);
      mail.setFrom(new InternetAddress(username)); // from = username
      mail.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
      mail.setSubject(subject, "utf-8");
      mail.setText(body, "utf-8", "html");
      Transport.send(mail);

      System.out.println("Gửi mail thành công!");
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
