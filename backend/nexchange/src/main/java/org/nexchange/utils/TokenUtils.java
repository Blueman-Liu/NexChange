package org.nexchange.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.nexchange.entity.User;
import org.nexchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {
    @Autowired
    private UserService userService;
    public Result<Object> expireOrUser(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        User user = (User) userService.getUserByJwt(header).getData();
        //token失效
        if (user == null) {
            return ResultUtils.error_401("请重新登录");
        }
        return ResultUtils.success("成功获取到user", user);
    }
}
