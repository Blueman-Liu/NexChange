package org.nexchange.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sun.net.httpserver.HttpsServer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jdk.jfr.Frequency;
import org.nexchange.entity.User;
import org.nexchange.mapper.UserMapper;
import org.nexchange.service.UserService;
import org.nexchange.utils.JwtUtils;
import org.nexchange.utils.MailSender;
import org.nexchange.utils.Result;
import org.nexchange.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.UUID;

@Tag(name = "登录接口")
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Resource
    private JwtUtils jwtUtils;

    @Operation(summary = "使用邮箱+密码登录")
    @PostMapping("/login/withPasswd")
    public Result<Object> loginWithPassword(@RequestBody User user, HttpServletRequest request) {
        User tmp = userService.getByAccount(user.getAccount());

        if (tmp == null) return ResultUtils.error_401("查无此用户");

        String saltedPasswd = DigestUtils.md5DigestAsHex((user.getPassword() + tmp.getSalt()).getBytes());
        if (tmp.getPassword().equals(saltedPasswd)) {
            String jwt = jwtUtils.createJwt(tmp);
            return ResultUtils.success("登陆成功", jwt);
        }
        return ResultUtils.error_401("密码错误，请重试！");
    }

    @Operation(summary = "使用邮箱+验证码登录")
    @PostMapping("/login/withVerCode")
    public Result<Object> loginWithVerCode(@RequestBody User user, String verCode, HttpServletRequest request) {
        User tmp = userService.getByAccount(user.getAccount());

        if (tmp == null) return ResultUtils.error_401("查无此用户");

        return (verify(user.getAccount(), verCode, request.getSession())
                .equals(ResultUtils.success("验证成功！", user.getAccount())))
                ? ResultUtils.success("登陆成功", jwtUtils.createJwt(tmp))
                : ResultUtils.error_401("验证失败，请重试！");

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
            return ResultUtils.success("已自动注册，登陆成功", jwtUtils.createJwt(user));
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
            return ResultUtils.success("注册成功", jwtUtils.createJwt(user));
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



    private Result<Object> verify(String email, String verCode, HttpSession session) {

        String rst = (String) session.getAttribute(email);
        if ("".equals(verCode)) {
            return ResultUtils.error_401("请输入验证码！");
        } else if (!verCode.equals(rst)) {  //验证session是否存在，不存在则验证码失效
            return ResultUtils.error_401("验证失败！验证码错误或已过期。");
        } else {
            session.removeAttribute(email);
            return ResultUtils.success("验证成功！", email);
        }
    }

    @GetMapping("/getUser")
    public Result<Object> getUserJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        User user = jwtUtils.toUser(jwtUtils.resolveJwt(header));
        return ResultUtils.success(user.getUsername(), user);
    }

}
