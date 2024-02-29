package org.nexchange.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.nexchange.entity.User;
import org.nexchange.utils.Result;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends IService<User> {
    public User getByAccount(String account);

    public User getByUsername(String username);

    public void addUser(User user);

    public boolean setUser(User newUser, User oldUser);

    public Result<Object> getUserByJwt(String header);
}
