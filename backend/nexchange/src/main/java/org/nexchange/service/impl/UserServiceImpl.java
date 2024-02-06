package org.nexchange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.nexchange.entity.User;
import org.nexchange.mapper.UserMapper;
import org.nexchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByAccount(String account) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getAccount, account);
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
}
