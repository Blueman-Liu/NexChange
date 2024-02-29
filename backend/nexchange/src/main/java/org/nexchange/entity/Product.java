package org.nexchange.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("products")
public class Product {
    @TableId(value = "productID", type = IdType.AUTO)
    private long productID;

    @TableField("productName")
    private String productName;

    @TableField("description")
    private String description;

    @TableField("sellerID")
    private long sellerID;

    @TableField("price")
    private double price;

    @TableField("status")
    private String status;

    @TableField("publicationDate")
    private LocalDateTime publicationDate;

    @TableField("imageURL")
    private String imageURL;

    @TableField("quality")
    private double quality;

    @TableField("categoryID")
    private long categoryID;
}
