package com.example.ShanruanShopping.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ShanruanShopping.entity.ShoppingCart;
import com.example.ShanruanShopping.entity.vo.ShoppingInfo;

import java.io.Serializable;

/**
 * 购物车(ShoppingCart)表服务接口
 *
 * @author bwmgd
 * @since 2021-04-25 09:50:11
 */
public interface ShoppingCartService extends IService<ShoppingCart> {

    ShoppingCart getShoppingCartByCommodityId(ShoppingCart shopCraft);

    IPage<ShoppingInfo> page(IPage<ShoppingInfo> page, Serializable userId);
}