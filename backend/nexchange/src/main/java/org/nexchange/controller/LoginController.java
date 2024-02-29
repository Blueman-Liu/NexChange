package org.nexchange.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.nexchange.entity.User;
import org.nexchange.mapper.UserMapper;
import org.nexchange.service.EmailService;
import org.nexchange.service.RedisService;
import org.nexchange.service.UserService;
import org.nexchange.utils.JwtUtils;
import org.nexchange.utils.MailSender;
import org.nexchange.utils.Result;
import org.nexchange.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "登录接口")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private RedisService redisService;

    @Autowired
    private EmailService emailService;

    @Operation(summary = "使用邮箱+密码登录")
    @PostMapping("/login/withPasswd")
    public Result<Object> loginWithPassword(@RequestBody User user, HttpServletRequest request) {
        User tmp = userService.getByAccount(user.getAccount());

        if (tmp == null) return ResultUtils.error_401("查无此用户");

        String saltedPasswd = DigestUtils.md5DigestAsHex((user.getPassword() + tmp.getSalt()).getBytes());
        if (tmp.getPassword().equals(saltedPasswd)) {
            String jwt = jwtUtils.createJwt(tmp);
            redisService.set(tmp.getAccount(), jwt, 7 * 24 * 60);
            return ResultUtils.success("登陆成功", jwt);
        }
        return ResultUtils.error_401("密码错误，请重试！");
    }

    @Operation(summary = "使用邮箱+验证码登录")
    @PostMapping("/login/withVerCode")
    public Result<Object> loginWithVerCode(@RequestBody User user, String verCode, HttpServletRequest request) {
        User tmp = userService.getByAccount(user.getAccount());

        if (tmp == null) return ResultUtils.error_401("查无此用户");
        if (verify(user.getAccount(), verCode, request.getSession())
                .equals(ResultUtils.success("验证成功！", user.getAccount()))) {
            String jwt = jwtUtils.createJwt(tmp);
            redisService.set(tmp.getAccount(), jwt, 7 * 24 * 60);
            return ResultUtils.success("登陆成功", jwt);
        } else {
            return ResultUtils.error_401("验证失败，请重试！");
        }
    }

    @Operation(summary = "使用微信登录")
    @PostMapping("/login/withWeChat")
    public Result<Object> loginWithWeChat(@RequestBody User user, HttpServletRequest request) {
        User tmp = userService.getByAccount(user.getAccount());

        if (tmp == null) {
            String salt = UUID.randomUUID().toString();
            user.setSalt(salt);
            String initPasswd = DigestUtils.md5DigestAsHex(("123456" + salt).getBytes());
            user.setPassword(initPasswd);
            user.setUsername("用户" + user.getAccount());
            userMapper.insert(user);
            String jwt = jwtUtils.createJwt(user);
            redisService.set(user.getAccount(), jwt, 7 * 24 * 60);
            return ResultUtils.success("已自动注册，登陆成功", jwt);
        }
        return ResultUtils.success("登陆成功");
    }

    @Operation(summary = "注册")
    @PostMapping("/register")
    public Result<Object> register(@RequestBody User user, @RequestParam("verCode") String verCode, HttpServletRequest request) {
        //验证邮箱格式
        if (!MailSender.isValidEmail(user.getAccount())) {
            return ResultUtils.error_401("邮箱格式错误");
        }

        User tmp = userService.getByAccount(user.getAccount());

        if (tmp != null) {
            return ResultUtils.error_401("该邮箱已被注册过，试试另一个？");
        }

        Result<Object> result = verify(user.getAccount(), verCode, request.getSession());
        if (result.equals(ResultUtils.success("验证成功！", user.getAccount()))) {
            userService.addUser(user);
            String jwt = jwtUtils.createJwt(user);
            redisService.set(user.getAccount(), jwt, 7 * 24 * 60);
            return ResultUtils.success("注册成功", jwt);
        }

        return result;
    }

    @Operation(summary = "忘记密码")
    @PostMapping("/resetPasswd")
    public Result<Object> resetPasswd(@RequestBody User user, @RequestParam String verCode, HttpServletRequest request) {
        User tmp = userService.getByAccount(user.getAccount());

        if (tmp == null) {
            return ResultUtils.error_401("该邮箱未被注册过，请重试！");
        }

        Result<Object> result = verify(user.getAccount(), verCode, request.getSession());
        if (result.equals(ResultUtils.success("验证成功！", user.getAccount()))) {
            String saltedPasswd =DigestUtils.md5DigestAsHex((user.getPassword() + tmp.getSalt()).getBytes());
            tmp.setPassword(saltedPasswd);
            userService.saveOrUpdate(tmp);
            return ResultUtils.success("修改成功");
        }
        return result;
    }

    private Result<Object> verify(String account, String verCode, HttpSession session) {

//        String rst = (String) session.getAttribute(email);
//        if ("".equals(verCode)) {
//            return ResultUtils.error_401("请输入验证码！");
//        } else if (!verCode.equals(rst)) {  //验证session是否存在，不存在则验证码失效
//            return ResultUtils.error_401("验证失败！验证码错误或已过期。");
//        } else {
//            session.removeAttribute(email);
//            return ResultUtils.success("验证成功！", email);
//        }
        String msg = emailService.verifyEmail(verCode, account);
        if (msg.equals("验证成功！")) {
            redisService.del(verCode);
            return ResultUtils.success(msg, account);
        }
        return ResultUtils.error_401(msg, account);
    }
    @GetMapping("/logout")
    public Result<Object> logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = jwtUtils.convertToken(header);
        DecodedJWT decodedJWT = jwtUtils.resolveJwt(header);
        User user = jwtUtils.toUser(decodedJWT);
        redisService.del(user.getAccount());
        //redisService.sAdd("black", new BlackItem(token, new Date()));
        log.info(user.getUsername() + " logout successfully!");
        return ResultUtils.success("登出成功");
    }



}
