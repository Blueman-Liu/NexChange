package org.nexchange.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.nexchange.service.EmailService;
import org.nexchange.service.RedisService;
import org.nexchange.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@Slf4j
@RestController//接受请求
@CrossOrigin //解决跨域
@RequestMapping("/email") //访问路径
public class EmailController {

    String receiver;

    //注入对象
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail4Verify")
    public Result<Object> sendEmailForVerify(@RequestBody String receiver, HttpServletRequest request) {
        System.out.println("发送邮件到" + receiver);
        return emailService.sendEmail4Verify(receiver, request);
    }

    @PostMapping("/sendEmail")
    public String sendEmail(String receiver, String content) {
        System.out.println("发送邮件到" + receiver);
        emailService.sendEmail(receiver, content);
        return "发送成功";
    }

//    @PostMapping("/verifyEmail")
//    public String verifyEmail(String verCode, HttpServletRequest request) {
//        System.out.println("验证-邮箱发送的验证码" + verCode);
//        return emailService.verifyEmail(verCode, request);
//    }

}
