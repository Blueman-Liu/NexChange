package org.nexchange.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.nexchange.entity.User;
import org.nexchange.service.FollowService;
import org.nexchange.service.UserService;
import org.nexchange.utils.JwtUtils;
import org.nexchange.utils.Result;
import org.nexchange.utils.ResultUtils;
import org.nexchange.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping("/getUser")
    public Result<Object> getUserJwt(HttpServletRequest request) {
        Result<Object> res = tokenUtils.expireOrUser(request);
        if (res.getCode() != 200) {
            return res;
        }
        User user = (User) res.getData();
        return ResultUtils.success("获取成功", user);
    }

    @PostMapping("/updateUser")
    public Result<Object> updateUser(@RequestBody User user, HttpServletRequest request) {
        Result<Object> res = tokenUtils.expireOrUser(request);
        if (res.getCode() != 200) {
            return res;
        }
        User oldUser = (User) res.getData();
        boolean setRes = userService.setUser(user, oldUser);
        if (setRes) {
            return ResultUtils.success("修改成功");
        }
        return ResultUtils.error_401("用户名已被使用，试试另一个怎么样？");
    }

    @PostMapping("/followUser")
    public Result<Object> followUser(@RequestBody User target, HttpServletRequest request) {
        Result<Object> res = tokenUtils.expireOrUser(request);
        if (res.getCode() != 200) {
            return res;
        }
        User user = (User) res.getData();
        followService.follow(target.getUserID(), user.getUserID());
        return ResultUtils.success("关注成功");
    }

    @PostMapping("/unfollowUser")
    public Result<Object> unfollowUser(@RequestBody User target, HttpServletRequest request) {
        Result<Object> res = tokenUtils.expireOrUser(request);
        if (res.getCode() != 200) {
            return res;
        }
        User user = (User) res.getData();
        followService.unfollow(target.getUserID(), user.getUserID());
        return ResultUtils.success("取消关注成功");
    }








}
