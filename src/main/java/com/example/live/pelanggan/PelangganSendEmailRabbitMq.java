package com.example.live.pelanggan;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Locale;
import java.time.LocalDate;
import java.nio.charset.StandardCharsets;
import java.io.File;
import org.springframework.core.io.ClassPathResource;
import java.util.Properties;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.env.Environment;
@Service
public class PelangganSendEmailRabbitMq {

  /**
   * Assigns a Consumer to receive the messages whenever there is one.
   * @param message
   */

  @Autowired private Environment env;
  
  @RabbitListener(queues = {"send.email.pelanggan"})
  public void receiveMessage(byte[] pelanggan){

    
    String str = new String(pelanggan, StandardCharsets.UTF_8);
    ArrayList<String> myList = new ArrayList<String>(Arrays.asList(str.split(",")));
    String to = myList.get(1);
    // Put sender's address
    String from = "manullang_d@yahoo.com";
    final String username = env.getProperty("spring.mail.username");//username generated by Mailtrap
    final String password = env.getProperty("spring.mail.password");//password generated by Mailtrap
    // Paste host address from the SMTP settings tab in your Mailtrap Inbox
    String host = env.getProperty("spring.mail.host");
    Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", env.getProperty("spring.mail.port"));
      //create the Session object
      Session session = Session.getInstance(props,
         new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
    }
         });
      try {
    //create a MimeMessage object
    Message message = new MimeMessage(session);
    //set From email field
    message.setFrom(new InternetAddress(from));
    //set To email field
    message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
    //set email subject field
    message.setSubject("Hi, new customer");
    //set the content of the email message
    message.setText("Welcome aboard customer "+myList.get(0)+".");
    //send the email message
    Transport.send(message);
    System.out.println("Email Message Sent Successfully");
      } catch (MessagingException e) {
        e.printStackTrace();
      }
    
      
        System.out.println("Received Message:" + myList.get(3));
        System.out.println();
      }


}