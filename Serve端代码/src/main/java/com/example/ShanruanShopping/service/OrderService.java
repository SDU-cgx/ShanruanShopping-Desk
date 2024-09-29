package com.example.ShanruanShopping.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ShanruanShopping.entity.Orders;
import com.example.ShanruanShopping.entity.vo.OrdersInfo;

import java.io.Serializable;
import java.util.Map;
import java.util.List;

/**
 * 订单(Order)表服务接口
 *
 * @author bwmgd
 * @since 2021-04-25 09:50:11
 */
public interface OrderService extends IService<Orders> {
    IPage<OrdersInfo> pageByStatus(IPage<OrdersInfo> page, Serializable status);
    boolean deleteOrderById(Serializable id);
    boolean updateStatusById(Serializable id,Serializable status);
    boolean updateCanReturnById(Serializable id,Serializable can_return);
    boolean updateEndTimeById(Serializable id,Serializable end_time);
    boolean updateCreTimeById(Serializable id,Serializable cre_time);
    long getMoneyByOrder();
    long getNumByOrder();
    List<Map<String,Object>> getShopMoney();

    List<Map<String,Object>> getOrderShopNum();
    List<Map<String,Object>> getTimeOrder();
}