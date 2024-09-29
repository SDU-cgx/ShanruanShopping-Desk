package com.example.ShanruanShopping.entity.vo;

import com.example.ShanruanShopping.entity.ShoppingCart;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShoppingInfo extends ShoppingCart {
    private String name;
    private Integer shopId;
    private String shopName;
    private Double price;
    private Double discount;
    private Integer inventory;
}
