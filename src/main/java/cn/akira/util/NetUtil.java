package cn.akira.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class NetUtil {
    public static void sendEmail(String targetEmail, String title, String content) throws MessagingException, UnsupportedEncodingException, GeneralSecurityException {

        String from = "akira.pub@qq.com";
        String host = "smtp.qq.com";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1510215527@qq.com", "qbgxkjbsncbfjcjc"); //发件人邮件用户名、密码
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from, "罗田田", "UTF8"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(targetEmail));
        message.setSubject(title);
        message.setText(content);
        Transport.send(message);
        System.out.println("Sent message successfully");
    }

}
