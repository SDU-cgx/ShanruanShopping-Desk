package com.example.ShanruanShopping.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

/**
 * 购物车(ShoppingCart)表实体类
 *
 * @author bwmgd
 * @since 2021-05-11 10:55:25
 */
@Data
@TableName("shopping_cart_view")
public class ShoppingCart implements Serializable {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    
    private Integer commodityId;
    
    private Integer quantity;
    
    private Integer userId;
    
}