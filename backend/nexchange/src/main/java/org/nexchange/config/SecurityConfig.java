//package org.nexchange.config;
//
//import com.alibaba.fastjson2.JSONObject;
//import com.alibaba.fastjson2.JSONWriter;
//import jakarta.annotation.Resource;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.nexchange.entity.vo.response.AuthorizeVO;
//import org.nexchange.filter.JwtAuthorizeFilter;
//import org.nexchange.service.UserService;
//import org.nexchange.utils.JwtUtils;
//import org.nexchange.utils.ResultUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//
//import java.io.IOException;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @Autowired
//    private UserService userService;
//
//    @Resource
//    private JwtAuthorizeFilter jwtFilter;
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(authorize -> authorize
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(authorize -> authorize
//                        .loginProcessingUrl("/api/auth/login")
//                        .successHandler(this::onAuthenticationSuccess)
//                        .failureHandler(this::onAuthenticationFailure))
//                .logout(authorize -> authorize
//                        .logoutUrl("/api/auth/logout")
//                        .logoutSuccessHandler(this::onLogoutSuccess))
//                .csrf(AbstractHttpConfigurer::disable)
//                //配置无状态
//                .sessionManagement(conf -> conf
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .httpBasic(withDefaults());
//        return http.build();
//    }
//
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication)
//            throws IOException, ServletException {
//        //获取UserDetails对象
//        //包含username， password， authorities
//        User user = (User) authentication.getPrincipal();
//        //获取token
//        String token = jwtUtils.createJwt(user, 1, "felix");
//        //封装vo
//        AuthorizeVO vo = new AuthorizeVO();
//        vo.setExpire(jwtUtils.getCreatedExpireTime());
//        vo.setToken(token);
//        String username = userService.getByAccount(user.getUsername()).getUsername();
//        vo.setUsername(username == null ? "unknown" : username);
//        //设置编码和格式
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("application/json");
//        response.getWriter().write(ResultUtils.success(vo).toJsonString());
//    }
//
//    public void onAuthenticationFailure(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        AuthenticationException exception)
//            throws IOException, ServletException {
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("application/json");
//        response.getWriter().write(ResultUtils.error_401("登录失败").toJsonString());
//    }
//
//    public void onLogoutSuccess(HttpServletRequest request,
//                                HttpServletResponse response,
//                                Authentication authentication)
//            throws IOException, ServletException {
//        response.getWriter().write("successfully logout");
//    }
//    //基于数据库的用户管理
////    @Bean
////    public UserDetailsService userDetailsService() {
////        return new DBUserDetailsManager();
////    }
//
//    //基于内存的用户管理
////    @Bean
////    public UserDetailsService userDetailsService() {
////        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
////        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
////        return manager;
////    }
//}
