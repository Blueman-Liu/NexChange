//package org.nexchange.config;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import jakarta.annotation.Resource;
//import org.nexchange.entity.User;
//import org.nexchange.mapper.UserMapper;
//import org.nexchange.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsPasswordService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.util.DigestUtils;
//
//import java.util.Collection;
//import java.util.LinkedList;
//import java.util.UUID;
//
//
//public class DBUserDetailsManager implements UserDetailsManager, UserDetailsPasswordService {
//    @Resource
//    private UserMapper userMapper;
//    @Resource
//    private UserService userService;
//    @Override
//    public UserDetails updatePassword(UserDetails user, String newPassword) {
//        return null;
//    }
//
//    @Override
//    public void createUser(UserDetails userDetails) {
//        User tmp = new User();
//        tmp.setAccount(userDetails.getUsername());
//        tmp.setUsername("用户" + userDetails.getUsername());
//        //设置盐值
//        String salt = UUID.randomUUID().toString();
//        tmp.setSalt(salt); // 把盐值存入数据库
//        String password = userDetails.getPassword();
//        password = DigestUtils.md5DigestAsHex((password + salt).getBytes());
//        tmp.setPassword(password);
//
//        userMapper.insert(tmp);
//    }
//
//    @Override
//    public void updateUser(UserDetails user) {
//
//    }
//
//    @Override
//    public void deleteUser(String username) {
//
//    }
//
//    @Override
//    public void changePassword(String oldPassword, String newPassword) {
//
//    }
//
//    @Override
//    public boolean userExists(String username) {
//        return false;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userService.getByAccount(username);
//
//        if (user == null) {
//            throw new UsernameNotFoundException(username);
//        } else {
//            Collection<GrantedAuthority> authorities = new LinkedList<>();
//            String saltedPasswd = DigestUtils.md5DigestAsHex((user.getPassword() + user.getSalt()).getBytes());
//            return new org.springframework.security.core.userdetails.User(
//                    user.getAccount(),
//                    //saltedPasswd,
//                    user.getPassword(),
//                    true,
//                    true,
//                    true,
//                    true,
//                    authorities);
//        }
//    }
//}
