package org.nexchange.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.nexchange.entity.User;
import org.nexchange.mapper.UserMapper;
import org.nexchange.service.RedisService;
import org.nexchange.service.UserService;
import org.nexchange.utils.JwtUtils;
import org.nexchange.utils.Result;
import org.nexchange.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisService redisService;

    @Override
    public User getByAccount(String account) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getAccount, account);
        return getOne(lambdaQueryWrapper);
    }

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, username);
        return getOne(lambdaQueryWrapper);
    }


    @Override
    public void addUser(User user) {
        user.setUsername("用户" + user.getAccount());
        //设置盐值
        String salt = UUID.randomUUID().toString();
        user.setSalt(salt); // 把盐值存入数据库
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        user.setPassword(password);

        userMapper.insert(user);
    }

    @Override
    public boolean setUser(User newUser, User oldUser) {
        User tmp = getByAccount(oldUser.getAccount());
        //处理修改过用户名的情况
        if (!tmp.getUsername().equals(newUser.getUsername())) {
            //有重名，重试
            if (!(getByUsername(newUser.getUsername()) == null)) {
                return false;
            }
            //无重名，可以修改
            oldUser.setUsername(newUser.getUsername());
        }
        //通用处理
        oldUser.setSex(newUser.getSex());
        oldUser.setImageURL(newUser.getImageURL());
        userMapper.updateById(oldUser);
        return true;
    }

    @Override
    public Result<Object> getUserByJwt(String header) {
        DecodedJWT decodedJWT = jwtUtils.resolveJwt(header);
//        if (decodedJWT == null) {
//            return ResultUtils.error_401("用户已登出，请重新登录");
//        }

        User user = jwtUtils.toUser(decodedJWT);

        //log.info(redisService.sMembers("black").toString());

        if (redisService.get(user.getAccount()) == null) {
            return ResultUtils.error_401("用户登录已过期，请重新登录");
        }
        if (!redisService.get(user.getAccount()).equals(jwtUtils.convertToken(header))) {
            return ResultUtils.error_401("用户已登出，请重新登录");
        }
        User queryUser = getById(user.getUserID());
        user.setSex(queryUser.getSex());
        user.setUsername(queryUser.getUsername());
        user.setImageURL(queryUser.getImageURL());

        return ResultUtils.success(user.getAccount(), user);
    }
}
