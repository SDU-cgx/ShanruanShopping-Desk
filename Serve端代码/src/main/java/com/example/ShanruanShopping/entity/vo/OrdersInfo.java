package com.example.ShanruanShopping.entity.vo;

import com.example.ShanruanShopping.entity.Orders;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrdersInfo extends Orders {
    // 商品名称
    private String name;
    //店铺名称
    private String username;
    // 买家电话
    private String phone;
    // 买家地址
    private String address;
}
