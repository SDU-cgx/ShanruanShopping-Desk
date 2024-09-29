package com.example.ShanruanShopping.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

/**
 * 商家(Sell)表实体类
 *
 * @author bwmgd
 * @since 2021-05-11 18:27:33
 */
@Data
@TableName("sell_view")
public class Sell implements Serializable {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    
    private String name;
    
    private String phone;
    
    //0:男,1:女
    private Integer sex;
    
    private String card;
    
    private String password;
    
    //0:待审核,1:正常,2:审核失败
    private Integer status;
    
    private Double wallet;
    
    private String idCardUp;
    
    private String idCardBack;

    private String avatar;
    
    //level
    private Integer level;

    private String address;
    
}