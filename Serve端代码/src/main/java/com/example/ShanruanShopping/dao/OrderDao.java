package com.example.ShanruanShopping.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.ShanruanShopping.entity.Orders;
import com.example.ShanruanShopping.entity.vo.OrdersInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 订单(Order)表数据库访问层
 *
 * @author bwmgd
 * @since 2021-04-25 09:50:11
 */
@Mapper
public interface OrderDao extends BaseMapper<Orders> {
    @Select("select * from orders o join commodity c on c.id = o.commodity_id join user u on u.id = o.user_id where o.status = #{status} or o.status = 6 ORDER BY o.id DESC")
    List<OrdersInfo> getOrdersByStatus(IPage<OrdersInfo> page, Serializable status);

    @Delete("DELETE FROM orders WHERE id = #{id}")
    int deleteByIdCustom(Serializable id);

    @Update("UPDATE orders SET status = #{status} where id = #{id}")
    int updateStatusById(Serializable id, Serializable status);

    @Update("UPDATE orders SET can_return = #{can_return} where id = #{id}")
    int updateReturnById(Serializable id, Serializable can_return);

    @Update("UPDATE orders SET end_time = #{end_time} where id = #{id}")
    int updateEndById(Serializable id, Serializable end_time);

    @Update("UPDATE orders SET creat_time = #{creat_time} where id = #{id}")
    int updateCreById(Serializable id, Serializable creat_time);

    @Select("SELECT SUM(amount) AS total_amount FROM `orders` WHERE status = 8 or status = 9;")
    Long getMoney();

    @Select("SELECT COUNT(*) AS total_orders FROM orders;")
    Long getOrderNum();

    @Select("SELECT s.name AS name, ROUND(SUM(o.amount), 2) AS num FROM `orders` o JOIN `shop` s ON o.shop_id = s.id GROUP BY s.name;")
    List<Map<String, Object>> getShopNameAndNum();

    @Select("SELECT s.name AS name,COUNT(o.id) AS num FROM `orders` o JOIN shop s ON o.shop_id = s.id GROUP BY s.name ORDER BY num DESC;")
    List<Map<String, Object>> getShopOrderNum();

    @Select("SELECT shop.name as name,YEAR(o.end_time) AS year,MONTH(o.end_time) AS month, SUM(o.amount) AS num FROM orders AS o JOIN shop ON o.shop_id = shop.id WHERE o.end_time IS NOT NULL GROUP BY shop.name,YEAR(o.end_time), MONTH(o.end_time) ORDER BY year, month;")
    List<Map<String,Object>> getOrderbyTime();
}