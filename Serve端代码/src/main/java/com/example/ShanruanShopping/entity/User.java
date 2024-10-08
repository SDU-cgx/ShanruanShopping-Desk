package com.example.ShanruanShopping.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

/**
 * 普通用户(User)表实体类
 *
 * @author bwmgd
 * @since 2021-05-11 10:55:25
 */
@Data
@TableName("user")
public class User implements Serializable {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    @TableField("username")
    private String name;

    private String phone;

    private String email;

    private String address;
    
    //0:男,1:女
    private Integer sex;
    
    private String card;
    
    private String password;
    
    //0:待审核,1:正常,2:审核失败
    private Integer status;
    
    private Double wallet;
    
    private Double points;

    private String avatar;
    
}