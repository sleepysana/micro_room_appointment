package cn.akira.thread;

import cn.akira.util.NetUtil;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

public class SendEmailThread implements Runnable {

    private String targetEmail;
    private String title;
    private String content;

    public SendEmailThread() {
    }

    public SendEmailThread(String targetEmail, String title, String content) {
        this.targetEmail = targetEmail;
        this.title = title;
        this.content = content;
    }

    public void setTargetEmail(String targetEmail) {
        this.targetEmail = targetEmail;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void run() {
        try {
            System.out.println("邮件发送中...");
            NetUtil.sendEmail(targetEmail, title, content);
            System.out.println("邮件发送成功");
        } catch (MessagingException | UnsupportedEncodingException | GeneralSecurityException e) {
            System.out.println("邮件发送失败:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
