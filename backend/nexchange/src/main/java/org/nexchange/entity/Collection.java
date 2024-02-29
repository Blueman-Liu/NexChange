package org.nexchange.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("collections")
public class Collection {
    @MppMultiId
    @TableField("userID")
    private Long userID;

    @MppMultiId
    @TableField("productID")
    private Long productID;

    @TableField("collectDateTime")
    private LocalDateTime collectDateTime;
}
