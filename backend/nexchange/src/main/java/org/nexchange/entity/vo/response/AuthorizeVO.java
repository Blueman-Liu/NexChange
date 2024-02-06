package org.nexchange.entity.vo.response;

import lombok.Data;

import java.util.Date;

@Data
public class AuthorizeVO {
    String username;
    String token;
//    String role;
    Date expire;

}
