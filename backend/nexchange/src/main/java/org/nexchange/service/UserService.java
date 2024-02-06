package org.nexchange.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.nexchange.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends IService<User> {
    public User getByAccount(String account);

    public void addUser(User user);
}
