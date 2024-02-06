package org.nexchange.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("`users`")
public class User {

    @TableId(value = "userID", type = IdType.AUTO)
    private Long userID;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("account")
    private String account;

    @TableField("regDateTime")
    private LocalDateTime regDateTime;

    @TableField("salt")
    private String salt;
}
