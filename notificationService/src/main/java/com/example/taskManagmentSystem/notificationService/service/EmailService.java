package com.example.taskManagmentSystem.notificationService.service;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.taskManagmentSystem.notificationService.models.User;
@Service
public class EmailService {
    private String email;
    private String password;

    /**
     * Parameterized constructor to get data from application properties.
     * 
     * @param robotEmail
     * @param robotPassword
     */
    public EmailService(
            @Value("${notification.robot.email}") final String robotEmail,
            @Value("${notification.robot.password}") final String robotPassword) {
        this.email = robotPassword;
        this.password = robotEmail;
    }

    public void sendEmail(User user, String message, String subject) {        
        // Create properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 465);
        
        // Create session
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
        
        try {
            // Create message
            MimeMessage emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(email));
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            emailMessage.setSubject(subject);
            
            // Set content type as HTML
            String htmlContent=(
            "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\">"
            + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
            + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
            + "<title>Document</title><style>"
            + "body{min-height: 90vh;background: #f5f5f5;font-family: sans-serif;display: flex;"
            + "justify-content: center;align-items: center;}"
            + ".heading{text-align: center;font-size: 40px;width: 50%;"
            + "display: block;line-height: 50px;margin: 30px auto 60px;text-transform: capitalize;}"
            + ".heading span{font-weight: 300;}.btn{width: 200px;height: 50px;"
            + "border-radius: 5px;background: #3f3f3f;color: #fff;display: block;"
            + "margin: auto;font-size: 18px;text-transform: capitalize;}"
            + "</style></head><body><div>"
            + "<h1 class=\"heading\">Dear" + user.getFirstName() + " "+ user.getLastName()
            + " ,\n<span>" + message + "</span>"
            + "</h1><button class=\"btn\">check status</button></div></body></html>");
            emailMessage.setContent(htmlContent, "text/html");
            
            // Send the email
            Transport.send(emailMessage);
            
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
