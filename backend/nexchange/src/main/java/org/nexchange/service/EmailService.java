package org.nexchange.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    //发送验证码
    String sendEmail4Verify(String receiver, HttpServletRequest request);

    String sendEmail(String receiver, String content);

    String verifyEmail(String verCode, HttpServletRequest request);
}
