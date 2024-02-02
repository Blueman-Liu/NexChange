package org.nexchange.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@TableName("transactions")
public class Transaction {
    @TableId(value = "transactionID", type = IdType.AUTO)
    private Long transactionID;

    @TableField("productID")
    private Long productID;

    @TableField("buyerID")
    private Long buyerID;

    @TableField("sellerID")
    private Long sellerID;

    @TableField("transactionDate")
    private LocalDateTime transactionDate;

    @TableField("transactionStatus")
    private String transactionStatus;
}
