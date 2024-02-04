package org.nexchange.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sun.net.httpserver.HttpsServer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jdk.jfr.Frequency;
import org.nexchange.entity.User;
import org.nexchange.mapper.UserMapper;
import org.nexchange.service.UserService;
import org.nexchange.utils.MailSender;
import org.nexchange.utils.Result;
import org.nexchange.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "登录接口")
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Operation(summary = "使用邮箱+密码登录")
    @PostMapping("/withPasswd")
    public Result<Object> loginWithPassword(@RequestBody User user, HttpServletRequest request) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getAccount, user.getAccount());
        User tmp = userService.getOne(lambdaQueryWrapper);

        if (tmp == null) return ResultUtil.error_401("查无此用户");

        if (tmp.getPassword().equals(user.getPassword())) {
            //todo: redis
            return ResultUtil.success("登陆成功");
        }
        return ResultUtil.error_401("密码错误，请重试！");
    }

    @Operation(summary = "使用邮箱+验证码登录")
    @PostMapping("/withVerCode")
    public Result<Object> loginWithVerCode(@RequestBody User user, String verCode, HttpServletRequest request) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getAccount, user.getAccount());
        User tmp = userService.getOne(lambdaQueryWrapper);

        if (tmp == null) return ResultUtil.error_401("查无此用户");

        return verify(user.getAccount(), verCode, request.getSession());

    }

    @Operation(summary = "使用微信登录")
    @PostMapping("/withWeChat")
    public Result<Object> loginWithWeChat(@RequestBody User user, HttpServletRequest request) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getAccount, user.getAccount());
        User tmp = userService.getOne(lambdaQueryWrapper);

        if (tmp == null) {
            user.setPassword("123456");
            user.setUsername("用户" + user.getAccount());
            userMapper.insert(user);
            return ResultUtil.success("已自动注册，登陆成功");
        }
        return ResultUtil.success("登陆成功");
    }

    @Operation(summary = "注册")
    @PostMapping("/register")
    public Result<Object> register(@RequestBody User user, @RequestParam("verCode") String verCode, HttpServletRequest request) {
        //验证邮箱格式
        if (!MailSender.isValidEmail(user.getAccount())) {
            return ResultUtil.error_401("邮箱格式错误");
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getAccount, user.getAccount());
        User tmp = userService.getOne(lambdaQueryWrapper);

        if (tmp != null) {
            return ResultUtil.error_401("该邮箱已被注册过，试试另一个？");
        }

        Result<Object> result = verify(user.getAccount(), verCode, request.getSession());
        if (result.equals(ResultUtil.success("验证成功！", user.getAccount()))) {
            user.setUsername("用户" + user.getAccount());
            userMapper.insert(user);
            return ResultUtil.success("注册成功");
        }

        return result;
    }

    @Operation(summary = "忘记密码")
    @PostMapping("/resetPasswd")
    public Result<Object> resetPasswd(@RequestBody User user, @RequestParam String verCode, HttpServletRequest request) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getAccount, user.getAccount());
        User tmp = userService.getOne(lambdaQueryWrapper);

        if (tmp == null) {
            return ResultUtil.error_401("该邮箱未被注册过，请重试！");
        }
        Result<Object> result = verify(user.getAccount(), verCode, request.getSession());
        if (result.equals(ResultUtil.success("验证成功！", user.getAccount()))) {
            if (user.getPassword().equals(tmp.getPassword())) {
                return ResultUtil.error_401("该密码与原密码相同，请重试!");
            }
            tmp.setPassword(user.getPassword());
            userService.saveOrUpdate(tmp);
            return ResultUtil.success("修改成功");
        }
        return result;
    }



    private Result<Object> verify(String email, String verCode, HttpSession session) {

        String rst = (String) session.getAttribute(email);
        if ("".equals(verCode)) {
            return ResultUtil.error_401("请输入验证码！");
        } else if (rst == null || !verCode.equals(rst)) {  //验证session是否存在，不存在则验证码失效
            return ResultUtil.error_401("验证失败！验证码错误或已过期。");
        } else {
            session.removeAttribute(email);
            return ResultUtil.success("验证成功！", email);
        }
    }

}
