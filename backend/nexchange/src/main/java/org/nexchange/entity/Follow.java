package org.nexchange.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("follows")
public class Follow {
    @MppMultiId
    @TableField("followerID")
    private Long followerID;

    @MppMultiId
    @TableField("followingID")
    private Long followingID;

    @TableField("followDateTime")
    private LocalDateTime followDateTime;
}
