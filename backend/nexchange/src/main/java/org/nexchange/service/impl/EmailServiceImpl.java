package org.nexchange.service.impl;

import org.nexchange.service.EmailService;
import org.nexchange.utils.MailSender;
import org.nexchange.utils.VerCodeGenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
@Service
public class EmailServiceImpl implements EmailService {

    //定义验证码
    private  String verCode = null;
    private  String content = null;

    @Autowired
    JavaMailSender jms;

    //读取配置文件邮箱账号参数
    @Value("${spring.mail.username}")
    private String sender;

    private String receiver;

    private Date sendDate = null;

    public void setReceiver(String receiver){
        this.receiver = receiver;
    }

    public String getVerCode() {
        return verCode;
    }

    //发送验证码
    @Override
    public String sendEmail4Verify(String receiver, HttpServletRequest request) {
        if(!MailSender.isValidEmail(receiver)) return "请输入正确的邮箱地址";

        setReceiver(receiver);
        String verCode = VerCodeGenUtil.getVerCode();

        System.out.println(verCode);

        this.verCode = verCode;
        HttpSession session = request.getSession();
        session.setAttribute(receiver, verCode);
        session.setMaxInactiveInterval(600);

        this.sendDate = new Date();

        //随机数用作验证
        return MailSender.sendEmail4Verify(jms, sender, receiver, verCode);

    }

    @Override
    public String sendEmail(String receiver, String content) {
        if(!MailSender.isValidEmail(receiver)) return "请输入正确的邮箱地址";

        setReceiver(receiver);
        this.content = content;

        //随机数用作验证
        return MailSender.sendEmail(jms, sender, receiver, content);
    }


    @Override
    public String verifyEmail(String verCode, HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute(receiver));

        return session.getAttribute(receiver)==null ? "验证码已过期" : verCode.equals(session.getAttribute(receiver)) ? "验证成功" : "验证失败";
    }

}
