package org.nexchange.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.nexchange.entity.BlackItem;
import org.nexchange.entity.User;
import org.nexchange.service.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtUtils {
    //@Value("${spring.security.jwt.key}")
    private final String key = "qwertyuiop";

    //@Value("${spring.security.jwt.expire}")
    private final int expire = 7;

    @Getter
    private Date createdExpireTime = new Date();

    private final Algorithm algorithm = Algorithm.HMAC256(key);

    @Resource
    private RedisService redisService;


    //供普通登录实现使用
    public String createJwt(User user) {
        Date expire = this.expireTime();
        this.createdExpireTime = expire;
        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("id", user.getUserID())
                .withClaim("username", user.getUsername())
                .withClaim("account", user.getAccount())
                .withExpiresAt(expire)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    //供security使用
//    public String createJwt(UserDetails userDetails, long id, String username) {
//
//        Date expire = this.expireTime();
//        this.createdExpireTime = expire;
//        return JWT.create()
//                .withClaim("id", id)
//                .withClaim("name", username)
////                .withClaim("authorities", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
//                .withExpiresAt(expire)
//                .withIssuedAt(new Date())
//                .sign(algorithm);
//
//    }

    public Date expireTime() {
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.HOUR, expire * 24);
        return calender.getTime();
    }

    public DecodedJWT resolveJwt(String headerToken) {
        String token = this.convertToken(headerToken);
        if (token == null) return null;
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT verify = jwtVerifier.verify(token);
//            if (redisService.sIsMember("black", new BlackItem(token, new Date()))) {
//                return null;
//            }
//            Date expireDate = verify.getExpiresAt();
//            return new Date().after(expireDate) ? null : verify;
            return verify;
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public String convertToken(String headerToken) {
        if (headerToken == null || !headerToken.startsWith("bearer ")) {
            return null;
        }
        return headerToken.substring(7);
    }
    public User toUser(DecodedJWT jwt) {
        if (jwt == null)
            return null;
        Map<String, Claim> claims = jwt.getClaims();
        User user = new User();
        user.setUsername(claims.get("username").asString());
        user.setUserID(claims.get("id").asLong());
        user.setAccount(claims.get("account").asString());
        return user;
    }

    //供security使用
//    public UserDetails toUserDetails(DecodedJWT jwt) {
//        Map<String, Claim> claims = jwt.getClaims();
//        return User
//                .withUsername(claims.get("username").asString())
//                .password("*******")
//                .authorities(claims.get("authorities").asArray(String.class))
//                .build();
//    }
}
