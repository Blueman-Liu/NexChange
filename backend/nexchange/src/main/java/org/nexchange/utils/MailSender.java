package org.nexchange.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.regex.Pattern;

public class MailSender {
    //向邮箱发送验证码
    public static String sendEmail4Verify(JavaMailSender jms, String sender, String receiver, String verCode) {
        //随机数用作验证
        //String verCode = VerCodeGenerateUtil.getVerCode();
        try {
            //建立邮件消息
            SimpleMailMessage mainMessage = new SimpleMailMessage();

            //发送者
            mainMessage.setFrom(sender);

            //接收者
            mainMessage.setTo(receiver);

            //发送的标题
            mainMessage.setSubject("邮箱验证");

            //发送的内容
            String msg = "您好！" + receiver + ",您正在使用邮箱验证，验证码：" + verCode + " （有效期为10分钟）。";
            mainMessage.setText(msg);

            //发送邮件
            jms.send(mainMessage);


        } catch (Exception e) {
            System.out.println(e);
            return ("发送邮件失败，请核对邮箱账号");
        }
        return "验证码已经发送您的邮箱，请前去邮箱查看，验证码是：" + verCode ;
    }


    //向邮箱发送任意内容
    public static String sendEmail(JavaMailSender jms, String sender, String receiver, String content) {

        try {
            //建立邮件消息
            SimpleMailMessage mainMessage = new SimpleMailMessage();

            //发送者
            mainMessage.setFrom(sender);

            //接收者
            mainMessage.setTo(receiver);

            //发送的标题
            mainMessage.setSubject("课程驳回意见");

            //发送的内容
            String msg = content;
            mainMessage.setText(msg);

            //发送邮件
            jms.send(mainMessage);


        } catch (Exception e) {
            System.out.println(e);
            return ("发送邮件失败，请核对邮箱账号");
        }
        return "意见已经发送您的邮箱，请前去邮箱查看。"  ;
    }

    //邮箱正则表达式匹配验证
    public static boolean isValidEmail(String email) {
        if ((email != null) && (!email.isEmpty())) {
            return Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email);
        }
        return false;
    }

}
