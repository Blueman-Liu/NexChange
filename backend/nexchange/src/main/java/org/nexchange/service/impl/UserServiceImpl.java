package org.nexchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.nexchange.entity.User;
import org.nexchange.mapper.UserMapper;
import org.nexchange.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
