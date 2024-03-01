package org.nexchange.config;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nexchange.entity.User;
import org.nexchange.service.UserService;
import org.nexchange.utils.JwtUtils;
import org.nexchange.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //获取token
        String token = request.getHeader("Authorization");
        System.out.println("从头部获取的token=" + token);
        //验证token
        User user = jwtUtils.toUser(jwtUtils.resolveJwt(token));
        if (null == user) {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(ResultUtils.error_401("请先登录").toJsonString());
            return false;
//                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "请先登录!");
        }
        return true;
    }
}
