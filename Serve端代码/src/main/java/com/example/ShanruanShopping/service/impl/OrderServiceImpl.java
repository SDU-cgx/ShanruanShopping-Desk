package com.example.ShanruanShopping.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ShanruanShopping.dao.OrderDao;
import com.example.ShanruanShopping.entity.Orders;
import com.example.ShanruanShopping.service.OrderService;
import com.example.ShanruanShopping.entity.vo.OrdersInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 订单(Order)表服务实现类
 *
 * @author bwmgd
 * @since 2021-04-25 09:50:11
 */
@Slf4j
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, Orders> implements OrderService {
    @Override
    public IPage<OrdersInfo> pageByStatus(IPage<OrdersInfo> page, Serializable status) {
        return page.setRecords(this.baseMapper.getOrdersByStatus(page, status));
    }

    @Transactional
    @Override
    public boolean deleteOrderById(Serializable id) {
        // 调用自定义的删除方法
        int rowsAffected = this.baseMapper.deleteByIdCustom(id);
        return rowsAffected > 0;
    }
    @Transactional
    @Override
    public boolean updateStatusById(Serializable id, Serializable status) {
        int rowsAffected = this.baseMapper.updateStatusById(id, status);
        return rowsAffected > 0;
    }

    @Transactional
    @Override
    public boolean updateCanReturnById(Serializable id, Serializable can_return) {
        int rowsAffected = this.baseMapper.updateReturnById(id, can_return);
        return rowsAffected > 0;
    }
    @Transactional
    @Override
    public boolean updateEndTimeById(Serializable id, Serializable end_time) {
        int rowsAffected = this.baseMapper.updateEndById(id, end_time);
        return rowsAffected > 0;
    }

    @Transactional
    @Override
    public boolean updateCreTimeById(Serializable id, Serializable cre_time) {
        int rowsAffected = this.baseMapper.updateCreById(id, cre_time);
        return rowsAffected > 0;
    }

    @Override
    public long getMoneyByOrder() {
        return this.baseMapper.getMoney();
    }

    @Override
    public long getNumByOrder() {
        return this.baseMapper.getOrderNum();
    }

    @Override
    public List<Map<String, Object>> getShopMoney() {
        return this.baseMapper.getShopNameAndNum();
    }

    @Override
    public List<Map<String, Object>> getOrderShopNum() {
        return this.baseMapper.getShopOrderNum();
    }

    @Override
    public List<Map<String, Object>> getTimeOrder() {
        return this.baseMapper.getOrderbyTime();
    }


}