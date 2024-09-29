package com.example.ShanruanShopping.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

/**
 * 店铺(Shop)表实体类
 *
 * @author bwmgd
 * @since 2021-05-11 10:55:25
 */
@Data
@TableName("shop_view")
public class Shop implements Serializable {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    
    private String name;
    
    private Integer sellerId;
    
    private Double score;
    
}