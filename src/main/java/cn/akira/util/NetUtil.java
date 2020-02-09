package cn.akira.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;

public class NetUtil {

    public static void sendEmail(String targetEmail, String title, String content) throws Exception {

        Map<String, String> emailConfigMap;
        try {
            emailConfigMap=ConfigUtil.getConfigMap("email");
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("读取邮箱配置失败了");
        }
        String from = emailConfigMap.get("address");
        String host = emailConfigMap.get("smtpHost");
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailConfigMap.get("address"), emailConfigMap.get("password")); //发件人邮件用户名、密码
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from, emailConfigMap.get("alias"), "UTF8"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(targetEmail));
        message.setSubject(title);
        message.setText(content);
        try {
            Transport.send(message);
            System.out.println("Sent email successfully");
        } catch (AuthenticationFailedException e) {
            System.err.println("motherfucker");
            e.printStackTrace();
        }
    }
}
